package nl.profict.albero.web;

import nl.profict.albero.AlberoException;

/**
 * Thrown by {@link ResponseEncoder response encoders} and {@link ContextDecoder context decoders}.
 *
 * @author levi_h
 */
public class CodecException extends AlberoException {
	/**
	 * Creates a codec exception.
	 *
	 * @param messageParts the parts of the exception message
	 */
	public CodecException(Object... messageParts) {
		super(messageParts);
	}

	/**
	 * Creates a codec exception.
	 *
	 * @param cause the cause of the exception
	 * @param messageParts the parts of the exception message
	 */
	public CodecException(Throwable cause, Object... messageParts) {
		super(cause, messageParts);
	}

	private final static long serialVersionUID = 20091224L;
}