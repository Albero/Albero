package nl.profict.albero.testing.trees

import nl.profict.albero.model.Context
import nl.profict.albero.model.DefaultContext
import nl.profict.albero.model.forms.Form

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