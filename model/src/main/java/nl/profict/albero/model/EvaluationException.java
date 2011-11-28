package nl.profict.albero.model;

import nl.profict.albero.AlberoException;

/**
 * Thrown by {@link Node nodes} and {@link ResultProvider result providers} when something goes wrong during evaluation.
 *
 * @author levi_h
 */
public class EvaluationException extends AlberoException {
	/**
	 * Creates an evaluation exception.
	 *
	 * @param messageParts the parts of the exception message
	 */
	public EvaluationException(Object... messageParts) {
		super(messageParts);
	}

	/**
	 * Creates an evaluation exception.
	 *
	 * @param cause the cause of the exception
	 * @param messageParts the parts of the exception message
	 */
	public EvaluationException(Throwable cause, Object... messageParts) {
		super(cause, messageParts);
	}

	private final static long serialVersionUID = 20090818L;
}