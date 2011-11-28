package nl.profict.albero.testing.builders.trees

import nl.profict.albero.utilities.Builder

class InformationBuilder extends Builder {
	def build(Object... parameters) {
		Map information = parameters[0]

		information[variable] = value
	}
}