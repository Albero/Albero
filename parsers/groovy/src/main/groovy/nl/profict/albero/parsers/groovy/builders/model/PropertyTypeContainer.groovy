package nl.profict.albero.parsers.groovy.builders.model

import nl.profict.albero.utilities.Builder

class PropertyTypeContainer extends Builder {
	PropertyTypeContainer() {
		addBuilder('type', PropertyTypeBuilder)

		setDynamicBuilder('type', 'name')
	}

	def build(Object... parameters) {
		Map propertyTypes = parameters[0]

		buildCollection('type', {propertyType ->
			propertyTypes[propertyType.name] = propertyType
		}, propertyTypes)
	}
}