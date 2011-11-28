package nl.profict.albero.model.forms;

import nl.profict.albero.model.model.PropertyType;

/**
 * The data type of a {@link FormElement form element}.
 *
 * @author levi_h
 */
public interface FormElementType {
	/**
	 * Returns the name of this form element type. It corresponds with the name of a {@link PropertyType property type}.
	 *
	 * @return this form element type's name
	 */
	String getName();

	/**
	 * Returns whether more than one answer can be given to form elements of this type.
	 *
	 * @return whether form elements of this type support multiple answers
	 */
	boolean isList();
}