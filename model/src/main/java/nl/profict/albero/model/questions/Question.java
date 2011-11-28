package nl.profict.albero.model.questions;

import nl.profict.albero.model.Context;
import nl.profict.albero.model.builders.FormBuilder;
import nl.profict.albero.model.text.Dictionary;
import nl.profict.albero.model.text.TextContainer;
import nl.profict.albero.model.validation.ValidationResults;

/**
 * A question. The given answer influences the results of a tree.
 *
 * @author levi_h
 * @see QuestionNode
 */
public interface Question extends TextContainer {
	/**
	 * Determines whether this question is answered in a certain context.
	 *
	 * @param context the context to use
	 * @return {@code true} if this question is answered in the given context, {@code false} otherwise
	 */
	boolean isAnswered(Context context);

	/**
	 * Validates the answers to this question in a certain context.
	 *
	 * @param context the context to validate
	 * @param dictionary the dictionary to use for validation messages
	 * @param validationResults the validation results to update
	 */
	void validate(Context context, Dictionary dictionary, ValidationResults validationResults);

	/**
	 * Asks this question.
	 *
	 * @param context the context in which the question should be asked
	 * @param validate whether to validate the given context
	 * @param dictionary the dictionary to use for form texts
	 * @param formBuilder the form builder to use
	 */
	void ask(Context context, boolean validate, Dictionary dictionary, FormBuilder formBuilder);
}