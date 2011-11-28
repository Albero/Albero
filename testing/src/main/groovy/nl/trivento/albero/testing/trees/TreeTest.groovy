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
package nl.trivento.albero.testing.trees

import nl.trivento.albero.model.Context
import nl.trivento.albero.model.DefaultContext
import nl.trivento.albero.model.forms.Form

class TreeTest {
	public String description

	private Context context

	private List expectations

	TreeTest(String description, String tree, String node, String role, Map information, List expectations) {
		this.description = description

		context = new DefaultContext()
		context.setVariable(Context.ALBERO, 'tree', tree)

		if (node) {
			context.setVariable(Context.ALBERO, 'node', node)
		}

		context.setVariable(Context.ALBERO, 'role', role)
		context.setVariable(Context.ALBERO, 'traversal_strategy', 'random') // TODO parameter

		information.each {String name, value ->
			context.setVariable(Context.INFORMATION, name, value)
		}

		this.expectations = expectations
	}

	Context getContext() {
		context
	}

	List checkExpectations(Form form) {
		List messages = []

		expectations.each {expectation ->
			expectation.check(context, form).each {message ->
				messages << "expectation in '${description}' did not meet (${message})"
			}
		}

		return messages
	}
}
