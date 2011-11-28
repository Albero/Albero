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
package nl.profict.albero.parsers.groovy.builders

import nl.profict.albero.model.Tree
import nl.profict.albero.utilities.Builder

class CompositeTreeBuilder extends Builder {
	private Map dynamicProperties

	CompositeTreeBuilder() {
		defaultProperty = 'code'

		dynamicProperties = [:]
	}

	def dynamicProperty(String name, value) {
		dynamicProperties[name] = value
	}

	def build(Object... parameters) {
		def configuration = parameters[0]

		def treeInformation = configuration.repository.locate(code)

		Tree tree = configuration.parsers[treeInformation.parser].parse(treeInformation.tree)

		dynamicProperties.each {String name, value ->
			tree.nodeCodes.each {nodeCode ->
				tree.findNode(nodeCode).setDynamicProperty(name, value)
			}
		}

		tree
	}
}
