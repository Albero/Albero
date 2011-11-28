package nl.profict.albero.parsers.groovy.builders

import nl.profict.albero.model.Tree
import nl.profict.albero.utilities.Builder

class CompositeTreeBuilder extends Builder {
	private Map dynamicProperties

	CompositeTreeBuilder() {
		defaultProperty = 'code'

		dynamicProperties = [:]
	}

	def dynamicProperty(String name, value) {
		dynamicProperties[name] = value
	}

	def build(Object... parameters) {
		def configuration = parameters[0]

		def treeInformation = configuration.repository.locate(code)

		Tree tree = configuration.parsers[treeInformation.parser].parse(treeInformation.tree)

		dynamicProperties.each {String name, value ->
			tree.nodeCodes.each {nodeCode ->
				tree.findNode(nodeCode).setDynamicProperty(name, value)
			}
		}

		tree
	}
}