package nl.profict.albero.model.validation;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import nl.profict.albero.model.Context;
import nl.profict.albero.model.model.Model;
import nl.profict.albero.model.text.Dictionary;

/**
 * Validates that a required property actually has a value.
 *
 * @author levi_h
 */
public class RequiredPropertyValidator implements PropertyValidator {
	/**
	 * Creates a required property validator.
	 */
	public RequiredPropertyValidator() {}

	public void validate(Context context, Model model, String propertyPath,
			Dictionary dictionary, ValidationResults validationResults) {
		Object value = context.getVariable(Context.INFORMATION, propertyPath);

		if ((value == null) || value.equals("") ||
				(model.getProperty(propertyPath).isList() && ((List<?>) value).isEmpty())) {
			String role = (String) context.getVariable(Context.ALBERO, "role");

			String propertyTranslation = dictionary.findTranslation(role, String.format("model.%s", propertyPath));
			Map<String, ?> parameters = Collections.singletonMap("property", propertyTranslation);

			validationResults.addError(new ValidationError(dictionary.findTranslation(role, ERROR_KEY, parameters)));
		}
	}

	/** The key that will be used for the validation error when validation fails. */
	public final static String ERROR_KEY = "validation.required";
}