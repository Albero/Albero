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
package nl.trivento.albero.model.external;

import java.util.Map;
import java.util.Set;

import nl.trivento.albero.model.Context;
import nl.trivento.albero.model.DefaultContext;
import nl.trivento.albero.model.builders.EvaluationContextBuilder;
import nl.trivento.albero.model.external.ExternalValueNode;
import nl.trivento.albero.model.external.ExternalValueProvider;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SuppressWarnings("javadoc")
@Test
public class ExternalValueNodeTest {
	private ExternalValueNode node;

	@BeforeMethod
	public void createNode() {
		node = new ExternalValueNode("external value", null, "forecast", "weather");
	}

	public void nodeShouldHaveCode() {
		assert node.getCode() != null;
		assert node.getCode().equals("external value");
	}

	public void nodeShouldBeEvaluatableInContextWithoutValue() {
		assert node.isEvaluatable(new DefaultContext());
	}

	public void nodeShouldNotBeEvaluatableInContextWithValue() {
		Context context = new DefaultContext();
		context.setVariable(Context.INFORMATION, "forecast", "cloudy");

		assert !node.isEvaluatable(context);
	}

	public void valueShouldBeAvailableAsContextVariableAfterEvaluation() {
		Context context = new DefaultContext();

		EvaluationContextBuilder evaluationContextBuilder = new EvaluationContextBuilder();
		evaluationContextBuilder.setContext(context);
		evaluationContextBuilder.addExternalValueProvider(new WeatherProvider());

		node.evaluate(evaluationContextBuilder.build(), false);

		Set<String> variableNames = context.getVariableNames(Context.INFORMATION);
		assert variableNames != null;
		assert variableNames.size() == 1;
		assert variableNames.contains("forecast");

		Object forecast = context.getVariable(Context.INFORMATION, "forecast");
		assert forecast != null;
		assert forecast.equals("sunny");
	}

	public void evaluationShouldNotResultInForm() {
		EvaluationContextBuilder evaluationContextBuilder = new EvaluationContextBuilder();
		evaluationContextBuilder.setContext(new DefaultContext());
		evaluationContextBuilder.addExternalValueProvider(new WeatherProvider());

		assert node.evaluate(evaluationContextBuilder.build(), false) == null;
	}

	private static class WeatherProvider implements ExternalValueProvider {
		public String getName() {
			return "weather";
		}

		public Object provideValue(Map<String, ?> parameters) {
			return "sunny";
		}
	}
}
