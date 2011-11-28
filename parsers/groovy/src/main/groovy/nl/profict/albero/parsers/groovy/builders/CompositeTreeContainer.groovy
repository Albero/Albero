package nl.profict.albero.parsers.groovy.builders

import nl.profict.albero.model.CompositeTree
import nl.profict.albero.utilities.Builder

class CompositeTreeContainer extends Builder {
	CompositeTreeContainer() {
		addBuilder('tree', CompositeTreeBuilder)
	}

	def build(Object... parameters) {
		def compositeTree = new CompositeTree(code)

		buildCollection('tree', {tree ->
			compositeTree.addTree tree
		}, parameters)

		compositeTree
	}
}