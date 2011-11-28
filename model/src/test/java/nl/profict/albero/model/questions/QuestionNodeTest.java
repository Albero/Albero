package nl.profict.albero.model.questions;

import nl.profict.albero.model.Context;
import nl.profict.albero.model.DefaultContext;
import nl.profict.albero.model.Node;
import nl.profict.albero.model.builders.EvaluationContextBuilder;
import nl.profict.albero.model.model.DefaultModel;
import nl.profict.albero.model.model.Property;
import nl.profict.albero.model.model.SimplePropertyType;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SuppressWarnings("all")
@Test
public class QuestionNodeTest {
	private Node questionNode;

	@BeforeMethod
	private void createQuestion() {
		DefaultModel model = new DefaultModel();
		model.addProperty(new Property("age", new SimplePropertyType("number")));

		questionNode = new QuestionNode("age", null, new SimpleQuestion(model, "age"));
	}

	public void questionNodeShouldHaveCode() {
		String code = questionNode.getCode();
		assert code != null;
		assert code.equals("age");
	}

	public void unansweredQuestionShouldBeEvaluatable() {
		Context context = new DefaultContext();
		context.setVariable(Context.ALBERO, "role", "en");

		assert questionNode.isEvaluatable(context);
	}

	public void evaluatingQuestionNodeWithoutAnsweringShouldResultInForm() {
		Context context = new DefaultContext();
		context.setVariable(Context.ALBERO, "role", "en");

		assert questionNode.evaluate(new EvaluationContextBuilder().setContext(context).build(), false) != null;
	}

	public void answeredQuestionShouldNotBeEvaluatable() {
		Context context = new DefaultContext();
		context.setVariable(Context.ALBERO, "role", "en");
		context.setVariable(Context.INFORMATION, "age", 27);

		assert !questionNode.isEvaluatable(context);
	}

	public void evaluationQuestionNodeWithPossibleAnswerShouldResultInNull() {
		DefaultContext context = new DefaultContext();
		context.setVariable(Context.ALBERO, "role", "en");
		context.setVariable(Context.INFORMATION, "age", 27);

		assert questionNode.evaluate(new EvaluationContextBuilder().setContext(context).build(), false) == null;
	}
}