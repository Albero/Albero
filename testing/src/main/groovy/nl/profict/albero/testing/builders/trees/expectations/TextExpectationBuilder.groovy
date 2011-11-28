package nl.profict.albero.testing.builders.trees.expectations

import nl.profict.albero.utilities.Builder

class TextExpectationBuilder extends Builder {
	def build(Object... parameters) {
		{Map texts ->
			String container = parameters[0]

			List messages = []

			if (texts.containsKey(type)) {
				if (texts[type] != value) {
					messages << "the text of type '${type}' in ${container} was '${texts[type]}', not '${value}'"
				}
			} else {
				messages << "no text of type '${type}' was found in ${container}"
			}

			messages
		}
	}
}