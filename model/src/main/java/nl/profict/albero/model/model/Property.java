package nl.profict.albero.model.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import nl.profict.albero.model.validation.PropertyValidator;

/**
 * A property of a model or result model.
 *
 * @author levi_h
 */
public class Property {
	private String name;
	private PropertyType type;

	private boolean list;

	private Set<PropertyValidator> validators;

	/**
	 * Creates a property that accepts a single value.
	 *
	 * @param name the name of the property
	 * @param type the type of the property
	 */
	public Property(String name, PropertyType type) {
		this(name, type, false);
	}

	/**
	 * Creates a property.
	 *
	 * @param name the name of the property
	 * @param type the type of the property
	 * @param list whether or not multiple values are accepted
	 */
	public Property(String name, PropertyType type, boolean list) {
		this.name = name;
		this.type = type;

		this.list = list;

		validators = new HashSet<PropertyValidator>();
	}

	/**
	 * Returns the name of this property.
	 *
	 * @return this property's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the type of this property.
	 *
	 * @return this property's type
	 */
	public PropertyType getType() {
		return type;
	}

	/**
	 * Returns whether or not multiple values are accepted.
	 *
	 * @return {@code true} if multiple values are accepted for this property, {@code false} otherwise
	 */
	public boolean isList() {
		return list;
	}

	/**
	 * Adds a property validator.
	 *
	 * @param validator the validator to add
	 */
	public void addValidator(PropertyValidator validator) {
		validators.add(validator);
	}

	/**
	 * Returns a set of all property validators that were added to this property.
	 *
	 * @return all of this property's validators
	 */
	public Set<PropertyValidator> getValidators() {
		return Collections.unmodifiableSet(validators);
	}

	@Override
	public String toString() {
		return String.format("property (name: '%s', type: %s)", name, type);
	}
}