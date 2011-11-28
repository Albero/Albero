package nl.profict.albero.model.forms;

import java.util.List;

import nl.profict.albero.model.text.TextContainer;

/**
 * An input form. It can contain either subforms or {@link FormElement form elements}.
 *
 * @author levi_h
 */
public interface Form extends TextContainer {
	/**
	 * Returns the name of this form. It is optional, but if it is present, it will prefix the names of its children.
	 *
	 * @return this form's name
	 */
	String getName();

	/**
	 * Returns all of the forms in this form.
	 *
	 * @return a list with all of this form's subforms
	 */
	List<Form> getForms();

	/**
	 * Returns all of the elements in this form.
	 *
	 * @return a list with all of this form's elements
	 */
	List<FormElement> getElements();
}