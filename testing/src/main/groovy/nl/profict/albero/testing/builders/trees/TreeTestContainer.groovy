package nl.profict.albero.testing.builders.trees

import nl.profict.albero.utilities.Builder

class TreeTestContainer extends Builder {
	public TreeTestContainer() {
		addBuilder('test', TreeTestBuilder)

		setDynamicBuilder('test', 'description')
	}

	def build(Object... parameters) {
		buildCollection('test', tree)
	}	
}