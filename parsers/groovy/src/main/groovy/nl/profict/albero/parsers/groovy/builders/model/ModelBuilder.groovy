package nl.profict.albero.parsers.groovy.builders.model

import nl.profict.albero.model.model.DefaultModel
import nl.profict.albero.utilities.Builder

class ModelBuilder extends Builder {
	ModelBuilder() {
		addBuilder('property', PropertyBuilder)

		setDynamicBuilder('property', 'name')
	}

	def build(Object... parameters) {
		def model = new DefaultModel()

		buildCollection('property', {property ->
			model.addProperty(property)
		}, parameters)

		model
	}
}