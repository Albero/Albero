package nl.profict.albero.parsers.groovy.builders.text

import nl.profict.albero.model.text.Dictionary
import nl.profict.albero.utilities.Builder

class TranslationBuilder extends Builder {
	def build(Object... parameters) {
		Dictionary dictionary = parameters[0]

		dictionary.addTranslation(role, key, translation)
	}
}