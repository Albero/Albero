package nl.profict.albero.parsers.groovy.builders.text

import nl.profict.albero.utilities.Builder

class TranslationContainer extends Builder {
	private TranslationBuilder translationBuilder

	TranslationContainer() {
		defaultProperty = 'role'

		addBuilder('translation', {->
			TranslationBuilder translationBuilder = new TranslationBuilder()
			translationBuilder.role = role

			translationBuilder
		})
	}

	def build(Object... parameters) {
		buildCollection('translation', parameters)
	}
}