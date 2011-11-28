package nl.profict.albero.parsers.groovy.builders.nodes

import nl.profict.albero.model.model.Model
import nl.profict.albero.model.questions.SimpleQuestion
import nl.profict.albero.parsers.groovy.builders.validation.PropertyValidationMixin

class SimpleQuestionBuilder extends AbstractQuestionBuilder {
	SimpleQuestionBuilder() {
		addMixin(new PropertyValidationMixin())
	}

	def build(Object... parameters) {
		Model model = parameters[0]

		def question = new SimpleQuestion(model, answers)
		question.renderingHint = renderingHint
		addTexts(question)

		applyMixins(question)

		question
	}
}