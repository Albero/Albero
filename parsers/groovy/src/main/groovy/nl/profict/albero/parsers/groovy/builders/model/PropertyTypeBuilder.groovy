package nl.profict.albero.parsers.groovy.builders.model

import nl.profict.albero.model.model.CompoundPropertyType
import nl.profict.albero.utilities.Builder

class PropertyTypeBuilder extends Builder {
	public PropertyTypeBuilder() {
		addBuilder('property', PropertyBuilder)

		setDynamicBuilder('property', 'name')

		defaultProperty = 'type'
	}

	def build(Object... parameters) {
		def propertyType = new CompoundPropertyType(name)

		buildCollection('property', {property ->
			propertyType.addProperty(property)
		}, parameters)

		propertyType
	}
}