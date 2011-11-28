package nl.profict.albero.parsers.groovy.builders

import nl.profict.albero.configuration.Configuration
import nl.profict.albero.utilities.Builder

class TreeContainer extends Builder {
	private Configuration configuration

	TreeContainer(Configuration configuration) {
		this.configuration = configuration

		addBuilder('tree', TreeBuilder)
		addBuilder('compositeTree', CompositeTreeContainer)
	}

	def build(Object... parameters) {
		buildProperty('tree') ?: buildProperty('compositeTree', configuration)
	}
}