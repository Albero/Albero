package nl.profict.albero.parsers.groovy.builders.nodes

import nl.profict.albero.utilities.Builder

class NodeContainer extends Builder {
	NodeContainer() {
		addBuilder('question', {-> new QuestionNodeBuilder(SimpleQuestionBuilder)})
		addBuilder('multipleChoiceQuestion', {-> new QuestionNodeBuilder(MultipleChoiceQuestionBuilder)})
		addBuilder('compoundQuestion', {-> new QuestionNodeBuilder(CompoundQuestionBuilder)})

		addBuilder('externalValue', ExternalValueNodeBuilder)
		addBuilder('values', ValueContainer)
	}

	def build(Object... parameters) {
		Map nodes = [:]

		['question', 'multipleChoiceQuestion', 'compoundQuestion', 'externalValue', 'values'].each {name ->
			buildCollection(name, {node ->
				nodes[node.code] = node
			}, parameters)
		}

		nodes
	}
}