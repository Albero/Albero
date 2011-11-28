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
package nl.trivento.albero.testing.builders.trees.expectations

import nl.trivento.albero.utilities.Builder
import nl.trivento.albero.model.forms.FormElementType

class FormElementTypeExpectationBuilder extends Builder {
	public FormElementTypeExpectationBuilder() {
		defaultProperty = 'name'
	}

	def build(Object... parameters) {
		{FormElementType type ->
			String formElementName = parameters[0]

			List messages = []

			if (name && (type.name != name)) {
				messages << "the type of form element '${formElementName}' is '${type.name}', not '${name}'"
			}

			if (list != null) {
				if (list && !type.list) {
					messages << "form element '${formElementName}' is not a list"
				} else if (!list && type.list) {
					messages << " form element '${formElementName}' is a list"
				}
			}

			messages
		}
	}
}
