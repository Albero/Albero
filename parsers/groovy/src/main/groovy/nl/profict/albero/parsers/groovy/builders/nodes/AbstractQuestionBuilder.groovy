package nl.profict.albero.parsers.groovy.builders.nodes

import nl.profict.albero.model.questions.AbstractQuestion
import nl.profict.albero.utilities.Builder

abstract class AbstractQuestionBuilder extends Builder {
	private Map texts

	AbstractQuestionBuilder() {
		texts = [:]
	}

	@Override
	void setProperty(String name, value) {
		def textMatcher = (name =~ /(.*)Text$/)

		if (textMatcher) {
			texts[textMatcher[0][1]] = value
		} else {
			super.setProperty(name, value)
		}
	}

	protected void addTexts(AbstractQuestion question) {
		texts.each {String type, String text ->
			question.addText(type, text)
		}
	}
}