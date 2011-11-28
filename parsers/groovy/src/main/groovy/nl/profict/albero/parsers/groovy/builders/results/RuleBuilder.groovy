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
