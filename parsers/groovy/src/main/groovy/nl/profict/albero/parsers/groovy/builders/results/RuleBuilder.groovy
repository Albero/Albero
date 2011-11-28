package nl.profict.albero.parsers.groovy.builders.results

import java.util.Map;

import nl.profict.albero.model.Context
import nl.profict.albero.model.EvaluationContext
import nl.profict.albero.model.ResultProvider
import nl.profict.albero.parsers.groovy.model.Condition
import nl.profict.albero.parsers.groovy.model.ContextCondition
import nl.profict.albero.utilities.Builder

class RuleBuilder extends Builder {
	private Condition condition
	private List effects

	RuleBuilder() {
		condition = [
			evaluate: {Context context ->
				Boolean.TRUE
			}
		] as Condition
		effects = []
	}

	def when(Condition condition) {
		this.condition = condition
	}

	def unless(Condition condition) {
		this.condition = condition.inverted()
	}

	def propertyMissing(String name) {
		new ContextCondition(name)
	}

	def set(String name, def value) {
		effects << {EvaluationContext evaluationContext ->
			Context context = evaluationContext.context

			if (value in String) {
				String role = context.getVariable(Context.ALBERO, 'role')

				Map parameters = [:]

				context.getVariableTypes().each {String variableType ->
					context.getVariableNames(variableType).each {String variableName ->
						parameters["${variableType}.${variableName}".toString()] = context.getVariable(variableType, variableName)
					}
				}

				value = evaluationContext.dictionary.findTranslation(role, value, parameters)
			}

			context.setVariable(Context.RESULTS, name, value)
		}
	}

	def copy(String resultName, String informationName) {
		effects << {EvaluationContext evaluationContext ->
			Context context = evaluationContext.context

			context.setVariable(Context.RESULTS, resultName, context.getVariable(Context.INFORMATION, informationName))
		}
	}

	def build(Object... parameters) {
		{EvaluationContext evaluationContext ->
			Context context = evaluationContext.context

			def result = condition.evaluate(evaluationContext.context)

			if (result?.booleanValue()) {
				effects.each {effect ->
					effect(evaluationContext)
				}
			}
		} as ResultProvider
	}
}