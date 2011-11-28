package nl.profict.albero.repositories;

import nl.profict.albero.configuration.ConfigurationElement;

/**
 * Stores information about trees.
 *
 * @author levi_h
 */
public interface Repository extends ConfigurationElement {
	/**
	 * Finds information on a tree by its code.
	 *
	 * @param code the code of the tree
	 * @return the information that is stored about the tree
	 * @throws RepositoryException when the tree information can't be located
	 */
	TreeInformation locate(String code) throws RepositoryException;
}