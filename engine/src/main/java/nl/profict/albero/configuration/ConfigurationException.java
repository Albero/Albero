package nl.profict.albero.configuration;

import nl.profict.albero.AlberoException;

/**
 * Thrown by a {@link Configuration configuration} when something goes wrong during initialisation.
 *
 * @author levi_h
 */
public class ConfigurationException extends AlberoException {
	/**
	 * Creates a configuration exception.
	 *
	 * @param messageParts the parts of the exception message
	 */
	public ConfigurationException(Object... messageParts) {
		super(messageParts);
	}

	/**
	 * Creates a configuration exception.
	 *
	 * @param cause the cause of the exception
	 * @param messageParts the parts of the exception message
	 */
	public ConfigurationException(Throwable cause, Object... messageParts) {
		super(cause, messageParts);
	}

	private final static long serialVersionUID = 20090831L;
}