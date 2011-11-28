package nl.profict.albero.testing.builders.trees.expectations

import nl.profict.albero.model.Context
import nl.profict.albero.model.forms.Form
import nl.profict.albero.testing.trees.TreeExpectation
import nl.profict.albero.utilities.Builder

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