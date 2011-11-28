package nl.profict.albero.parsers.groovy.builders.results

import nl.profict.albero.utilities.Builder

class RulesContainer extends Builder {
	RulesContainer() {
		addBuilder('rule', RuleBuilder)
	}

	def build(Object... parameters) {
		buildCollection('rule')
	}
}