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

import nl.trivento.albero.model.Context
import nl.trivento.albero.model.forms.Form
import nl.trivento.albero.testing.trees.TreeExpectation
import nl.trivento.albero.utilities.Builder

class FormExpectationBuilder extends Builder {
	FormExpectationBuilder() {
		addBuilder('form', FormExpectationBuilder)
		addBuilder('element', FormElementExpectationBuilder)
		addBuilder('text', TextExpectationBuilder)
	}

	def build(Object... parameters) {
		[check: {Context context, Form form ->
			List messages = []

			if (name) {
				if (form.name != name) {
					messages << "the name of the form is '${form.name}', not '${name}'"
				}
			}

			List subformExpectations = buildCollection('form')
			List subforms = form.forms

			if (subforms.size() == subformExpectations.size()) {
				forms.eachWithIndex {subform, index ->
					messages += formExpectations[index](context, subform)
				}
			} else {
				messages << "the form contains ${subforms.size()} subform${(subforms.size() == 1) ? '' : 's'}, not ${subformExpectations.size()}"
			}

			List elementExpectations = buildCollection('element')
			List elements = form.elements

			if (elements.size() == elementExpectations.size()) {
				elements.eachWithIndex {element, index ->
					messages += elementExpectations[index](element)
				}
			} else {
				messages << "the form contains ${elements.size()} element${(elements.size() == 1) ? '' : 's'}, not ${elementExpectations.size()}"
			}

			buildCollection('text', {textExpectation ->
				messages += textExpectation(form.texts)
			}, 'the form')

			messages
		}] as TreeExpectation
	}
}
