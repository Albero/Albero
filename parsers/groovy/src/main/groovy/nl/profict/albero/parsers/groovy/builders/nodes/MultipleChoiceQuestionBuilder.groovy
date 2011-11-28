package nl.profict.albero.parsers.groovy.builders.nodes

import nl.profict.albero.model.model.Model
import nl.profict.albero.model.questions.MultipleChoiceQuestion
import nl.profict.albero.parsers.groovy.builders.validation.PropertyValidationMixin

class MultipleChoiceQuestionBuilder extends AbstractQuestionBuilder {
	boolean multipleOptionsAllowed
	List options

	MultipleChoiceQuestionBuilder() {
		addMixin(new PropertyValidationMixin())

		options = []
	}

	def build(Object... parameters) {
		Model model = parameters[0]

		def question = new MultipleChoiceQuestion(options, model, answers)
		question.renderingHint = renderingHint
		addTexts(question)

		applyMixins(question)

		question
	}
}