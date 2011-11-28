package nl.profict.albero.model.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A property type that contains a number of subproperties.
 *
 * @author levi_h
 */
public class CompoundPropertyType implements PropertyType {
	private String name;

	private List<Property> subproperties;

	/**
	 * Creates a compound property type.
	 *
	 * @param name the name of the property type
	 */
	public CompoundPropertyType(String name) {
		this.name = name;

		subproperties = new ArrayList<Property>();
	}

	public String getName() {
		return name;
	}

	/**
	 * Adds a property to this property type.
	 *
	 * @param property the property to add
	 */
	public void addProperty(Property property) {
		subproperties.add(property);
	}

	public List<Property> getSubproperties() {
		return Collections.unmodifiableList(subproperties);
	}

	@Override
	public String toString() {
		return String.format("compound property type (name: '%s', subproperties: %s)", name, subproperties);
	}
}