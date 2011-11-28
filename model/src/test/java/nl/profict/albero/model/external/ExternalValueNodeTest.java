package nl.profict.albero.model.external;

import java.util.Map;
import java.util.Set;

import nl.profict.albero.model.Context;
import nl.profict.albero.model.DefaultContext;
import nl.profict.albero.model.builders.EvaluationContextBuilder;

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