/* Copyright 2011-2012 Profict Holding 
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package nl.trivento.albero.utilities

import nl.trivento.albero.AlberoException

abstract class Builder {
	private Map builderFactories
	private List optionalBuilderFactories
	private def dynamicBuilder
	private Map builders

	protected Builder delegate
	private List delegateExclusions

	private List mixins

	protected Map properties
	protected String defaultProperty

	protected Builder() {
		builderFactories = [:]
		optionalBuilderFactories = []
		builders = [:]

		delegateExclusions = []

		mixins = []

		properties = [:]
	}

	protected void addBuilder(String name, builderFactory) {
		builderFactories[name] = builderFactory
		builders[name] = []
	}

	protected void addOptionalBuilder(String name, builderFactory) {
		addBuilder(name, builderFactory)
		builders[name] << instantiateBuilder(builderFactory)

		optionalBuilderFactories << name
	}

	protected void setDynamicBuilder(String name, String property) {
		dynamicBuilder = [name: name, property: property]
	}

	private def instantiateBuilder(builderFactory) {
		(builderFactory instanceof Closure) ? builderFactory() : builderFactory.newInstance()
	}

	protected void doNotDelegate(String name) {
		delegateExclusions << name
	}

	protected void addMixin(def mixin) {
		mixins << mixin
	}

	def methodMissing(String name, args) {
		if (delegate && !delegateExclusions.contains(name)) {
			delegate."$name"(*args)
		} else {
			def mixin = mixins.find {it.respondsTo(name)}

			if (mixin) {
				mixin."$name"(*args)
			} else {
				boolean dynamic = !builderFactories.containsKey(name)

				if (dynamic && (dynamicBuilder == null)) {
					throw new AlberoException("${getClass().simpleName} does not have a builder named '${name}'.")
				} else {
					String builderName = dynamic ? dynamicBuilder['name'] : name
					boolean optional = optionalBuilderFactories.contains(builderName)

					def builder = optional ? builders[builderName][0] : instantiateBuilder(builderFactories[builderName])

					if (dynamic) {
						builder."${dynamicBuilder['property']}" = name
					}

					List mutableArgs = args as List

					if (!mutableArgs.empty && !((mutableArgs[0] instanceof Map) || (mutableArgs[0] instanceof Closure))) {
						builder."${builder.defaultProperty}" = mutableArgs[0]

						mutableArgs = mutableArgs.tail()
					}

					if (!mutableArgs.empty && (mutableArgs[0] instanceof Map)) {
						mutableArgs[0].each {property, value ->
							builder."$property" = value
						}
					}

					if (!mutableArgs.empty && (mutableArgs[-1] instanceof Closure)) {
						Closure configurer = mutableArgs[-1]

						configurer.delegate = builder
						configurer.resolveStrategy = Closure.DELEGATE_FIRST

						configurer()
					}

					if (!optional) {
						builders[dynamic ? dynamicBuilder['name'] : name] << builder
					}

					builder
				}
			}
		}
	}

	def getProperty(String name) {
		if (hasProperty(name)) {
			metaClass.getProperty(getClass(), this, name, true, false)
		} else if (builderFactories.containsKey(name)) {
			def builderFactory = builderFactories[name]

			builders[name] << ((builderFactory instanceof Closure) ? builderFactory() : builderFactory.newInstance())

			builders[name][-1]
		} else if (!delegate || (respondsTo('propertyMissing')[0].declaringClass.name != getClass().name)) {
			propertyMissing(name)
		} else {
			delegate.getProperty(name)
		}
	}

	def propertyMissing(String name) {
		properties[name]
	}

	void setProperty(String name, value) {
		if ((delegate == null) || delegateExclusions.contains(name)) {
			if (hasProperty(name)) {
				metaClass.setProperty(getClass(), this, name, value, true, false)
			} else {
				properties[name] = value
			}
		} else {
			delegate.setProperty(name, value)
		}
	}

	protected abstract def build(Object... parameters)

	protected def buildProperty(String name, Object... parameters) {
		builders[name].empty ? null : builders[name][-1].build(parameters)
	}

	protected List buildCollection(String name, Object... parameters) {
		buildCollection(name, null, parameters)
	}

	protected List buildCollection(String name, Closure action, Object... parameters) {
		builders[name].collect {builder ->
			def result = builder.build(parameters)

			if (action) {
				action.call(result)
			}

			result
		}
	}

	protected void applyMixins(Object... parameters) {
		mixins.each {mixin ->
			mixin.apply(parameters)
		}
	}
}
