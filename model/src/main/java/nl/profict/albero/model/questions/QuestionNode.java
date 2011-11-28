package nl.profict.albero.model.questions;

import nl.profict.albero.model.AbstractNode;
import nl.profict.albero.model.Context;
import nl.profict.albero.model.EvaluationContext;
import nl.profict.albero.model.EvaluationException;
import nl.profict.albero.model.builders.BuilderException;
import nl.profict.albero.model.builders.FormBuilder;
import nl.profict.albero.model.forms.Form;
import nl.profict.albero.model.text.Dictionary;
import nl.profict.albero.model.validation.ValidationResults;

/**
 * A node that needs a {@link Question question} to be answered.
 *
 * @author levi_h
 */
public class QuestionNode extends AbstractNode {
	private Question question;

	/**
	 * Creates a question node.
	 *
	 * @param code the code for the node
	 * @param group the group for the node
	 * @param question the question asked
	 */
	public QuestionNode(String code, String group, Question question) {
		super(code, group);

		this.question = question;
 	}

	@Override
	public boolean isEvaluatable(Context context) {
		return super.isEvaluatable(context) && !question.isAnswered(context);
	}

	public Form evaluate(EvaluationContext evaluationContext, boolean validate) throws EvaluationException {
		Form form;

		Context context = evaluationContext.getContext();
		Dictionary dictionary = evaluationContext.getDictionary();

		if (question.isAnswered(context) && (!validate || isQuestionValid(context, dictionary))) {
			form = null;
		} else {
			try {
				FormBuilder formBuilder = new FormBuilder();
				question.ask(context, validate, dictionary, formBuilder);
				form = formBuilder.build();
			} catch (BuilderException exception) {
				throw new EvaluationException(exception, "can't build evaluation");
			}
		}

		return form;
	}

	private boolean isQuestionValid(Context context, Dictionary dictionary) {
		ValidationResults validationResults = new ValidationResults();
		question.validate(context, dictionary, validationResults);
		return !validationResults.hasErrors();
	}

	@Override
	public String toString() {
		return String.format("question node (question: %s)", question);
	}
}