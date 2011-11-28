package nl.profict.albero.parsers.groovy.builders.results

import nl.profict.albero.parsers.groovy.builders.model.ModelBuilder
import nl.profict.albero.utilities.Builder

class ResultsContainer extends Builder {
	ResultsContainer(Map propertyTypes) {
		addOptionalBuilder('model', ModelBuilder)
		addOptionalBuilder('rules', RulesContainer)
	}

	def build(Object... parameters) {
		[
			model: buildProperty('model', parameters),
			providers: buildProperty('rules')
		]
	}
}