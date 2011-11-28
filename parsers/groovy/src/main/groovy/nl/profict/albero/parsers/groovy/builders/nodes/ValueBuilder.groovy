package nl.profict.albero.parsers.groovy.builders.nodes

import nl.profict.albero.utilities.Builder

class ValueBuilder extends Builder {
	ValueBuilder() {
		defaultProperty = 'value'
	}

	def build(Object... parameters) {
		value
	}
}