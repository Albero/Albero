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
package nl.profict.albero.parsers.groovy.builders.nodes

import nl.profict.albero.model.external.ExternalValueNode
import nl.profict.albero.model.model.Model

class ExternalValueNodeBuilder extends NodeBuilder {
	ExternalValueNodeBuilder() {
		addBuilder('parameter', ParameterBuilder)
	}

	protected ExternalValueNode buildNode(String code, String group, Model model) {
		def node = new ExternalValueNode(code, group, property, from)

		buildCollection('parameter', {String name, Object value ->
			node.addParameter(name, value)
		})

		node
	}
}
