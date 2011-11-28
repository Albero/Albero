package nl.profict.albero.parsers.groovy.builders.nodes

import nl.profict.albero.model.AbstractNode
import nl.profict.albero.model.Context
import nl.profict.albero.model.Node
import nl.profict.albero.model.NodeCondition
import nl.profict.albero.model.model.Model
import nl.profict.albero.parsers.groovy.model.Condition
import nl.profict.albero.parsers.groovy.model.ContextCondition
import nl.profict.albero.utilities.Builder

abstract class NodeBuilder extends Builder {
	String code
	String group

	protected Condition condition

	private Map dynamicProperties

	protected NodeBuilder() {
		dynamicProperties = [:]
	}

	def when(Condition condition) {
		this.condition = condition
	}

	def unless(Condition condition) {
		this.condition = condition.inverted()
	}

	def dynamicProperty(String name, value) {
		dynamicProperties[name] = value
	}

	def propertyMissing(String name) {
		Map properties = super.@properties 

		properties.containsKey(name) ? properties[name] : new ContextCondition(name)
	}

	def build(Object... parameters) {
		Model model = parameters[0]

		AbstractNode node = buildNode(code ?: "node-${++nodeCount}", group, model)

		if (condition) {
			node.addCondition([applies: {Context context ->
				def result = condition.evaluate(context)

				(result != null) && result.booleanValue()
			}] as NodeCondition)
		}

		dynamicProperties.each {name, value ->
			node.setDynamicProperty(name, value)
		}

		node
	}

	protected abstract AbstractNode buildNode(String code, String group, Model model)

	private static int nodeCount = 0
}