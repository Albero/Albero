package nl.profict.albero;

import nl.profict.albero.utilities.StringUtilities;

/**
 * Root of the Albero exception hierarchy.
 *
 * @author levi_h
 */
public class AlberoException extends RuntimeException {
	/**
	 * Creates an Albero exception.
	 *
	 * @param messageParts the parts of the exception message
	 */
	public AlberoException(Object... messageParts) {
		super(StringUtilities.join(messageParts));
	}

	/**
	 * Creates an Albero exception.
	 *
	 * @param cause the cause of the exception
	 * @param messageParts the parts of the exception message
	 */
	public AlberoException(Throwable cause, Object... messageParts) {
		super(StringUtilities.join(messageParts), cause);
	}

	private final static long serialVersionUID = 20090818L;
}