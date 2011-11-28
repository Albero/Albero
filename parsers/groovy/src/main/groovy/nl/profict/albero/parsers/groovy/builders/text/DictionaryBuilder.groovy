package nl.profict.albero.parsers.groovy.builders.text

import nl.profict.albero.model.text.DefaultDictionary
import nl.profict.albero.utilities.Builder

class DictionaryBuilder extends Builder {
	DictionaryBuilder() {
		addBuilder('translation', TranslationBuilder)
		addBuilder('withRole', TranslationContainer)
	}

	def build(Object... parameters) {
		DefaultDictionary dictionary = new DefaultDictionary()

		['translation', 'withRole'].each {name ->
			buildCollection(name, dictionary)
		}

		dictionary
	}
}