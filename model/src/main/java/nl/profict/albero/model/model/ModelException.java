package nl.profict.albero.model.model;

import nl.profict.albero.AlberoException;

/**
 * Thrown by a {@link Model model} when it is used inappropriately.
 *
 * @author levi_h
 */
public class ModelException extends AlberoException {
	/**
	 * Creates a model exception.
	 *
	 * @param messageParts the parts of the exception message
	 */
	public ModelException(Object... messageParts) {
		super(messageParts);
	}

	/**
	 * Creates a model exception.
	 *
	 * @param cause the cause of the exception
	 * @param messageParts the parts of the exception message
	 */
	public ModelException(Throwable cause, Object... messageParts) {
		super(cause, messageParts);
	}

	private final static long serialVersionUID = 20090930L;
}