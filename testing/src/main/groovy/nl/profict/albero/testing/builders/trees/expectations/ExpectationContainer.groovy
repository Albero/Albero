package nl.profict.albero.testing.builders.trees.expectations

import nl.profict.albero.utilities.Builder

class ExpectationContainer extends Builder {
	ExpectationContainer() {
		addBuilder('result', ResultExpectationBuilder)
		addBuilder('form', FormExpectationBuilder)
	}

	def build(Object... parameters) {
		List expectations = []

		['result', 'form'].each {name ->
			expectations.addAll buildCollection(name)
		}

		expectations
	}
}