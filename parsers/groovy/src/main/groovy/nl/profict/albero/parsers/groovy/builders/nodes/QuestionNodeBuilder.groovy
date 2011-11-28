package nl.profict.albero.parsers.groovy.builders.nodes

import nl.profict.albero.model.model.Model
import nl.profict.albero.model.questions.QuestionNode
import nl.profict.albero.utilities.Builder

class QuestionNodeBuilder extends NodeBuilder {
	private Builder questionBuilder

	public QuestionNodeBuilder(def questionBuilderFactory) {
		questionBuilder = (questionBuilderFactory instanceof Closure) ? questionBuilderFactory() : questionBuilderFactory.newInstance()

		delegate = questionBuilder
		doNotDelegate 'code'
		doNotDelegate 'group'
	}

	QuestionNode buildNode(String code, String group, Model model) {
		new QuestionNode(code, group, questionBuilder.build(model))
	}
}