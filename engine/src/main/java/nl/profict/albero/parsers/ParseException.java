package nl.profict.albero.parsers;

import nl.profict.albero.AlberoException;

/**
 * Thrown by {@link Parser parsers} when they can't parse a tree.
 *
 * @author levi_h
 */
public class ParseException extends AlberoException {
	/**
	 * Creates a parse exception.
	 *
	 * @param messageParts the parts of the exception message
	 */
	public ParseException(Object... messageParts) {
		super(messageParts);
	}

	/**
	 * Creates a parse exception.
	 *
	 * @param cause the cause of the exception
	 * @param messageParts the parts of the exception message
	 */
	public ParseException(Throwable cause, Object... messageParts) {
		super(cause, messageParts);
	}

	private final static long serialVersionUID = 20090818L;
}