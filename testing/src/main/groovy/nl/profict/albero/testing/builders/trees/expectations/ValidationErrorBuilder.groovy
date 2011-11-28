package nl.profict.albero.testing.builders.trees.expectations

import nl.profict.albero.utilities.Builder

class ValidationErrorBuilder extends Builder {
	public ValidationErrorBuilder() {
		defaultProperty = 'error'
	}

	def build(Object... parameters) {
		error
	}
}