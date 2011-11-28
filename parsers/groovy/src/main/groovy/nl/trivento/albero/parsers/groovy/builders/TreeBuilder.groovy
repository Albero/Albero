/* Copyright 2011-2012 Profict Holding 
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package nl.trivento.albero.parsers.groovy.builders

import nl.trivento.albero.model.model.Model
import nl.trivento.albero.model.text.Dictionary
import nl.trivento.albero.model.Tree
import nl.trivento.albero.parsers.groovy.builders.model.PropertyTypeContainer
import nl.trivento.albero.parsers.groovy.builders.model.ModelBuilder
import nl.trivento.albero.parsers.groovy.builders.nodes.NodeContainer
import nl.trivento.albero.parsers.groovy.builders.results.ResultsContainer
import nl.trivento.albero.parsers.groovy.builders.text.DictionaryBuilder
import nl.trivento.albero.utilities.Builder

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
			text: new nl.trivento.albero.model.model.SimplePropertyType('text'),
			number: new nl.trivento.albero.model.model.SimplePropertyType('number'),
			boolean: new nl.trivento.albero.model.model.SimplePropertyType('boolean'),
			date: new nl.trivento.albero.model.model.SimplePropertyType('date')
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
