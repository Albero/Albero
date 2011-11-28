package nl.profict.albero.parsers.groovy.builders.validation

import nl.profict.albero.model.validation.RequiredPropertyValidator

class PropertyValidationMixin {
	private List validators

	PropertyValidationMixin() {
		validators = []
	}

	def required() {
		validators << new RequiredPropertyValidator()
	}

	def apply(Object... parameters) {
		validators.each {validator ->
			parameters.each {parameter ->
				parameter.addValidator(validator)
			}
		}
	}
}