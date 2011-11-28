package nl.profict.albero.model.model;

import java.util.Collections;
import java.util.List;

/**
 * A property type without subproperties.
 *
 * @author levi_h
 */
public class SimplePropertyType implements PropertyType {
	private String name;

	/**
	 * Creates a simple property type.
	 *
	 * @param name the name of the property type
	 */
	public SimplePropertyType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<Property> getSubproperties() {
		return Collections.emptyList();
	}

	@Override
	public String toString() {
		return String.format("simple property type (name: '%s')", name);
	}
}