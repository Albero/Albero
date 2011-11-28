package nl.profict.albero.model.validation;

/**
 * A validation error.
 *
 * @author levi_h
 */
public class ValidationError {
	private String message;

	/**
	 * Creates a validation error.
	 *
	 * @param message a description of the error
	 */
	public ValidationError(String message) {
		this.message = message;
	}

	/**
	 * Returns this validation error's message.
	 *
	 * @return the message that describes this validation error
	 */
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return String.format("validation error (message: '%s')", message);
	}
}