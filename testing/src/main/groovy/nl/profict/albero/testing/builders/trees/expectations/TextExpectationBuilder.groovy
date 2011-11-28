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
package nl.profict.albero.testing.builders.trees.expectations

import nl.profict.albero.utilities.Builder

class TextExpectationBuilder extends Builder {
	def build(Object... parameters) {
		{Map texts ->
			String container = parameters[0]

			List messages = []

			if (texts.containsKey(type)) {
				if (texts[type] != value) {
					messages << "the text of type '${type}' in ${container} was '${texts[type]}', not '${value}'"
				}
			} else {
				messages << "no text of type '${type}' was found in ${container}"
			}

			messages
		}
	}
}
