package nl.profict.albero.testing.builders.trees

import nl.profict.albero.utilities.Builder

class TreeTestContainerContainer extends Builder {
	public TreeTestContainerContainer() {
		addBuilder('tests', TreeTestContainer)
	}

	def build(Object... parameters) {
		buildProperty('tests')
	}	
}