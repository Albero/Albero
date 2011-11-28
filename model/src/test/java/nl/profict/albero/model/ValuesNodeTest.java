package nl.profict.albero.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import nl.profict.albero.model.builders.EvaluationContextBuilder;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SuppressWarnings("all")
@Test
public class ValuesNodeTest {
	private ValuesNode node;

	@BeforeMethod
	public void createNode() {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("colours", Arrays.asList("Red", "Green", "Blue"));

		node = new ValuesNode("values", null, values);
	}

	public void nodeShouldHaveCode() {
		assert node.getCode() != null;
		assert node.getCode().equals("values");
	}

	public void nodeShouldBeEvaluatableInContextWithoutValues() {
		Context context = new DefaultContext();

		assert node.isEvaluatable(context);
	}

	public void valuesShouldBeAvailableAsContextVariablesAfterEvaluation() {
		Context context = new DefaultContext();

		node.evaluate(new EvaluationContextBuilder().setContext(context).build(), false);

		Set<String> variableNames = context.getVariableNames(Context.INFORMATION);
		assert variableNames != null;
		assert variableNames.size() == 1;
		assert variableNames.contains("colours");

		Object colours = context.getVariable(Context.INFORMATION, "colours");
		assert colours != null;
		assert colours.equals(Arrays.asList("Red", "Green", "Blue"));
	}

	public void evaluationShouldNotResultInForm() {
		assert node.evaluate(new EvaluationContextBuilder().setContext(new DefaultContext()).build(), false) == null;
	}

	public void nodeShouldNotBeEvaluatableInContextWithValues() {
		Context context = new DefaultContext();
		context.setVariable(Context.INFORMATION, "colours", Arrays.asList("Cyan", "Yellow", "Magenta", "Black"));

		assert !node.isEvaluatable(context);
	}
}