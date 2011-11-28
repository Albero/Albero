package nl.profict.albero.model;

import nl.profict.albero.AlberoException;

/**
 * Thrown by {@link Context contexts} when they are used incorrectly.
 *
 * @author levi_h
 */
public class ContextException extends AlberoException {
	/**
	 * Creates a context exception.
	 *
	 * @param messageParts the parts of the exception message
	 */
	public ContextException(Object... messageParts) {
		super(messageParts);
	}

	/**
	 * Creates a context exception.
	 *
	 * @param cause the cause of the exception
	 * @param messageParts the parts of the exception message
	 */
	public ContextException(Throwable cause, Object... messageParts) {
		super(cause, messageParts);
	}

	private final static long serialVersionUID = 20090818L;
}