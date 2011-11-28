package nl.profict.albero.model.model;

import java.util.List;

/**
 * The type of a {@link Property property}.
 *
 * @author levi_h
 */
public interface PropertyType {
	/**
	 * Returns the name of this property type.
	 *
	 * @return this property type's name
	 */
	String getName();

	/**
	 * Returns all of the subproperties of this property type.
	 *
	 * @return this property type's child properties
	 */
	List<Property> getSubproperties();
}