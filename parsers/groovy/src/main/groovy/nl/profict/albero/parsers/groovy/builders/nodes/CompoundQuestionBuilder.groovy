package nl.profict.albero.parsers.groovy.builders.nodes

import nl.profict.albero.model.questions.CompoundQuestion

class CompoundQuestionBuilder extends AbstractQuestionBuilder {
	CompoundQuestionBuilder() {
		addBuilder('question', SimpleQuestionBuilder)
		addBuilder('multipleChoiceQuestion', MultipleChoiceQuestionBuilder)
	}

	def build(Object... parameters) {
		def compoundQuestion = new CompoundQuestion()
		addTexts(compoundQuestion)

		['question', 'multipleChoiceQuestion'].each {name ->
			buildCollection(name, {question ->
				compoundQuestion.addQuestion(question)
			}, parameters)
		}

		compoundQuestion
	}
}