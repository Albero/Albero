package nl.profict.albero.model.builders;

import nl.profict.albero.AlberoException;

/**
 * Thrown by a builder if it is used inappropriately.
 *
 * @author levi_h
 */
public class BuilderException extends AlberoException {
	/**
	 * Creates a builder exception.
	 *
	 * @param messageParts the parts of the exception message
	 */
	public BuilderException(Object... messageParts) {
		super(messageParts);
	}

	/**
	 * Creates a builder exception.
	 *
	 * @param cause the cause of the exception
	 * @param messageParts the parts of the exception message
	 */
	public BuilderException(Throwable cause, Object... messageParts) {
		super(cause, messageParts);
	}

	private final static long serialVersionUID = 20090820L;
}