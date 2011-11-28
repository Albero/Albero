package nl.profict.albero.parsers.groovy.builders.nodes

import nl.profict.albero.utilities.Builder

class ParameterBuilder extends Builder {
	def build(Object... parameters) {
		[name, value]
	}
}