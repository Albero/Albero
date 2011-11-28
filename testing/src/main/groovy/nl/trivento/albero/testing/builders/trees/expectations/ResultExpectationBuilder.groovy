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
package nl.trivento.albero.testing.builders.trees.expectations

import nl.trivento.albero.model.Context
import nl.trivento.albero.model.forms.Form
import nl.trivento.albero.testing.trees.TreeExpectation
import nl.trivento.albero.utilities.Builder

class ResultExpectationBuilder extends Builder {
	def build(Object... parameters) {
		[check: {Context context, Form form ->
			List messages = []

			if (!context.getVariableNames(Context.RESULTS).contains(variable)) {
				messages << "the context does not contain a result variable named '${variable}'"
			} else {
				def actualValue = context.getVariable(Context.RESULTS, variable)

				if (actualValue != value) {
					messages << "the value of result variable '${variable}' is ${actualValue}, not ${value}"
				}
			}

			messages
		}] as TreeExpectation
	}
}
