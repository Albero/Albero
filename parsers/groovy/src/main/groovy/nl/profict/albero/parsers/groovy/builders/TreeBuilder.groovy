package nl.profict.albero.parsers.groovy.builders

import nl.profict.albero.model.model.Model
import nl.profict.albero.model.text.Dictionary
import nl.profict.albero.model.Tree
import nl.profict.albero.parsers.groovy.builders.model.PropertyTypeContainer
import nl.profict.albero.parsers.groovy.builders.model.ModelBuilder
import nl.profict.albero.parsers.groovy.builders.nodes.NodeContainer
import nl.profict.albero.parsers.groovy.builders.results.ResultsContainer
import nl.profict.albero.parsers.groovy.builders.text.DictionaryBuilder
import nl.profict.albero.utilities.Builder

class TreeBuilder extends Builder {
	TreeBuilder() {
		defaultProperty = 'code'

		addOptionalBuilder('dictionary', DictionaryBuilder)
		addBuilder('types', PropertyTypeContainer)
		addOptionalBuilder('model', ModelBuilder)
		addOptionalBuilder('nodes', NodeContainer)
		addOptionalBuilder('results', ResultsContainer)
	}

	def build(Object... parameters) {
		Map propertyTypes = [
			text: new nl.profict.albero.model.model.SimplePropertyType('text'),
			number: new nl.profict.albero.model.model.SimplePropertyType('number'),
			boolean: new nl.profict.albero.model.model.SimplePropertyType('boolean'),
			date: new nl.profict.albero.model.model.SimplePropertyType('date')
		]
		buildProperty('types', propertyTypes)

		Dictionary dictionary = buildProperty('dictionary')
		Model model = buildProperty('model', propertyTypes)
		Map nodes = buildProperty('nodes', model)
		Map results = buildProperty('results', propertyTypes)

		[
			getCode: {-> code},
			getDictionary: {-> dictionary},
			getModel: {-> model},
			getNodeCodes: {-> nodes.keySet() as List},
			findNode: {code -> nodes[code]},
			getResultModel: {-> results['model']},
			getResultProviders: {-> results['providers']},
			toString: {-> "Groovy tree (code: $code)".toString()}
		] as Tree
	}
}