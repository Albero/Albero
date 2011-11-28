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
