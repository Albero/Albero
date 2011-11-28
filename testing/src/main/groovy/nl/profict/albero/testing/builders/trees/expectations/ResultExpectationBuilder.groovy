package nl.profict.albero.testing.builders.trees.expectations

import nl.profict.albero.model.Context
import nl.profict.albero.model.forms.Form
import nl.profict.albero.testing.trees.TreeExpectation
import nl.profict.albero.utilities.Builder

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