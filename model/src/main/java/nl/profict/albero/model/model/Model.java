package nl.profict.albero.model.model;

import nl.profict.albero.model.Tree;

/**
 * The model of a {@link Tree tree}.
 *
 * @author levi_h
 */
public interface Model {
	/**
	 * Determines whether this model contains a property with a certain path.
	 *
	 * @param path the path to the property
	 * @return {@code true} if this model contains a property with the given path or {@code false} if it doesn't
	 */
	boolean hasProperty(String path);

	/**
	 * Finds a property.
	 *
	 * @param path the path to the property
	 * @return the property with the given path or {@code null} if the property could not be found
	 * @throws ModelException when the property with the given path can't be found
	 */
	Property getProperty(String path) throws ModelException;
}