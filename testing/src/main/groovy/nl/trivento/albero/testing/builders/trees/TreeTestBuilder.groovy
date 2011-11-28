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
package nl.trivento.albero.testing.builders.trees

import nl.trivento.albero.testing.builders.trees.expectations.ExpectationContainer
import nl.trivento.albero.testing.trees.TreeTest
import nl.trivento.albero.utilities.Builder

class TreeTestBuilder extends Builder {
	public TreeTestBuilder() {
		addBuilder('information', InformationBuilder)
		addBuilder('expectations', ExpectationContainer)
	}

	def build(Object... parameters) {
		String tree = parameters[0]

		Map information = [:]

		buildCollection('information', information)

		new TreeTest(description, tree, node, role ?: 'en', information, buildProperty('expectations'))
	}	
}
