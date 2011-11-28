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
package nl.trivento.albero.parsers.groovy.builders.nodes

import nl.trivento.albero.model.AbstractNode
import nl.trivento.albero.model.Context
import nl.trivento.albero.model.Node
import nl.trivento.albero.model.NodeCondition
import nl.trivento.albero.model.model.Model
import nl.trivento.albero.parsers.groovy.model.Condition
import nl.trivento.albero.parsers.groovy.model.ContextCondition
import nl.trivento.albero.utilities.Builder

abstract class NodeBuilder extends Builder {
	String code
	String group

	protected Condition condition

	private Map dynamicProperties

	protected NodeBuilder() {
		dynamicProperties = [:]
	}

	def when(Condition condition) {
		this.condition = condition
	}

	def unless(Condition condition) {
		this.condition = condition.inverted()
	}

	def dynamicProperty(String name, value) {
		dynamicProperties[name] = value
	}

	def propertyMissing(String name) {
		Map properties = super.@properties 

		properties.containsKey(name) ? properties[name] : new ContextCondition(name)
	}

	def build(Object... parameters) {
		Model model = parameters[0]

		AbstractNode node = buildNode(code ?: "node-${++nodeCount}", group, model)

		if (condition) {
			node.addCondition([applies: {Context context ->
				def result = condition.evaluate(context)

				(result != null) && result.booleanValue()
			}] as NodeCondition)
		}

		dynamicProperties.each {name, value ->
			node.setDynamicProperty(name, value)
		}

		node
	}

	protected abstract AbstractNode buildNode(String code, String group, Model model)

	private static int nodeCount = 0
}
