package nl.profict.albero.testing.builders.trees

import nl.profict.albero.testing.builders.trees.expectations.ExpectationContainer
import nl.profict.albero.testing.trees.TreeTest
import nl.profict.albero.utilities.Builder

class TreeTestBuilder extends Builder {
	public TreeTestBuilder() {
		addBuilder('information', InformationBuilder)
		addBuilder('expectations', ExpectationContainer)
	}

	def build(Object... parameters) {
		String tree = parameters[0]

		Map information = [:]

		buildCollection('information', information)

		new TreeTest(description, tree, node, role ?: 'en', information, buildProperty('expectations'))
	}	
}