package nl.profict.albero.repositories;

import nl.profict.albero.AlberoException;

/**
 * Thrown by a {@link Repository repository} when something goes wrong while locating tree information.
 *
 * @author levi_h
 */
public class RepositoryException extends AlberoException {
	/**
	 * Creates a repository exception.
	 *
	 * @param messageParts the parts of the exception message
	 */
	public RepositoryException(Object... messageParts) {
		super(messageParts);
	}

	/**
	 * Creates a repository exception.
	 *
	 * @param cause the cause of the exception
	 * @param messageParts the parts of the exception message
	 */
	public RepositoryException(Throwable cause, Object... messageParts) {
		super(cause, messageParts);
	}

	private final static long serialVersionUID = 20090818L;
}