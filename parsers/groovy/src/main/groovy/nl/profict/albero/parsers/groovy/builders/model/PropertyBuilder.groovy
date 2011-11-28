package nl.profict.albero.parsers.groovy.builders.model

import nl.profict.albero.model.model.Property
import nl.profict.albero.parsers.groovy.builders.validation.PropertyValidationMixin
import nl.profict.albero.utilities.Builder

class PropertyBuilder extends Builder {
	PropertyBuilder() {
		addMixin(new PropertyValidationMixin())

		defaultProperty = 'type'
	}

	def build(Object... parameters) {
		Map propertyTypes = parameters[0]

		Property property = new Property(name, propertyTypes[type], list && list.booleanValue())
		applyMixins(property)

		property
	}
}