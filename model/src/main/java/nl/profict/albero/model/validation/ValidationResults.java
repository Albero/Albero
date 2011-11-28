package nl.profict.albero.model.validation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import nl.profict.albero.model.Context;

/**
 * The results of validating a {@link Context context}.
 *
 * @author levi_h
 */
public class ValidationResults {
	private List<ValidationError> errors;

	/**
	 * Creates validation results.
	 */
	public ValidationResults() {
		errors = new LinkedList<ValidationError>();
	}

	/**
	 * Adds a validation error.
	 *
	 * @param error the validation error to add
	 */
	public void addError(ValidationError error) {
		errors.add(error);
	}

	/**
	 * Determines whether these validation results contains errors.
	 *
	 * @return {@code true} if any valudation errors have been added, {@code false} otherwise
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	/**
	 * Returns all added validation errors.
	 *
	 * @return a list with all validation errors
	 */
	public List<ValidationError> getErrors() {
		return Collections.unmodifiableList(errors);
	}

	@Override
	public String toString() {
		return String.format("validation results (errors: %s)", errors);
	}
}