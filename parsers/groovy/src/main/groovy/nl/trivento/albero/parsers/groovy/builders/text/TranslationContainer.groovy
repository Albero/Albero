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
package nl.trivento.albero.parsers.groovy.builders.text

import nl.trivento.albero.utilities.Builder

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
