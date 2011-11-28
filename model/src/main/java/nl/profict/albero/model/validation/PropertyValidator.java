package nl.profict.albero.model.validation;

import nl.profict.albero.model.Context;
import nl.profict.albero.model.model.Model;
import nl.profict.albero.model.text.Dictionary;

/**
 * Validates a context variable.
 *
 * @author levi_h
 */
public interface PropertyValidator {
	/**
	 * Validates a context variable and adds a {@link ValidationError error} to a set of {@link ValidationResults
	 * validation results} when validation fails.
	 *
	 * @param context the context that contains the variable to validate
	 * @param model the model that contains the property
	 * @param propertyPath the path to the property to validate
	 * @param dictionary the dictionary that contains the validation messages
	 * @param validationResults the validation results to update
	 */
	void validate(Context context, Model model, String propertyPath,
		Dictionary dictionary, ValidationResults validationResults);
}