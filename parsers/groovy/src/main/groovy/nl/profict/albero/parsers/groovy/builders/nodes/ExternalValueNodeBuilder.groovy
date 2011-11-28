package nl.profict.albero.parsers.groovy.builders.nodes

import nl.profict.albero.model.external.ExternalValueNode
import nl.profict.albero.model.model.Model

class ExternalValueNodeBuilder extends NodeBuilder {
	ExternalValueNodeBuilder() {
		addBuilder('parameter', ParameterBuilder)
	}

	protected ExternalValueNode buildNode(String code, String group, Model model) {
		def node = new ExternalValueNode(code, group, property, from)

		buildCollection('parameter', {String name, Object value ->
			node.addParameter(name, value)
		})

		node
	}
}