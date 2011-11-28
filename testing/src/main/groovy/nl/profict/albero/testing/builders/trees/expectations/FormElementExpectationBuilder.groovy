package nl.profict.albero.testing.builders.trees.expectations

import nl.profict.albero.model.forms.FormElement
import nl.profict.albero.utilities.Builder

class FormElementExpectationBuilder extends Builder {
	FormElementExpectationBuilder() {
		addBuilder('text', TextExpectationBuilder)
		addBuilder('type', FormElementTypeExpectationBuilder)
		addBuilder('validationError', ValidationErrorBuilder)
	}

	def build(Object... parameters) {
		{FormElement element ->
			List messages = []

			if (name && (element.name != name)) {
				messages << "the name of the form element is '${element.name}', not '${name}'"
			}

			def typeExpectation = buildProperty('type', element.name)

			if (typeExpectation) {
				messages += typeExpectation(element.type)
			}

			if (renderingHint && (element.renderingHint != renderingHint)) {
				messages << "the rendering hint of form element '${element.name}' is '${element.renderingHint}', not '${renderingHint}'"
			}

			if (options && (element.options != options)) {
				messages << "the options of form element '${element.name}' are '${element.options}', not '${options}'"
			}

			Set validationErrors = [] as Set

			buildCollection('validationError', {validationError ->
				validationErrors << validationError
			})

			if (validationErrors != (element.validationErrors as Set)) {
				messages << "the validation errors of form element '${element.name}' are ${element.validationErrors}, not ${validationErrors}"
			}

			buildCollection('text', {textExpectation ->
				messages += textExpectation(element.texts)
			}, "form element '${element.name}'")

			messages
		}
	}
}