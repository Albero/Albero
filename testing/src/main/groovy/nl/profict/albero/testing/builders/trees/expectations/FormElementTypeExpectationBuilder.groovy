package nl.profict.albero.testing.builders.trees.expectations

import nl.profict.albero.utilities.Builder
import nl.profict.albero.model.forms.FormElementType

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