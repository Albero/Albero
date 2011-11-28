package nl.profict.albero.model.forms;

import java.util.List;

import nl.profict.albero.model.text.TextContainer;

/**
 * An element in a {@link Form form}.
 *
 * @author levi_h
 */
public interface FormElement extends TextContainer {
	/**
	 * Returns the name of this form element. It will be used as the name of the context variable that contains the
	 * input to this form element as well and should therefore be unique across a form.
	 *
	 * @return this form element's name
	 */
	String getName();

	/**
	 * Returns the data type of this form element.
	 *
	 * @return this form element's data type
	 */
	FormElementType getType();

	/**
	 * Returns a hint as to how this form element should be rendered.
	 *
	 * @return this form element's rendering hint (may be {@code null})
	 */
	String getRenderingHint();

	/**
	 * Returns the errors that were encountered when validating the context variable that contains the input to this
	 * form element.
	 *
	 * @return this form element's validation errors (may be empty, but not {@code null})
	 */
	List<String> getValidationErrors();

	/**
	 * Returns the possible values of this form element. This is an optional property.
	 *
	 * @return the possible values of this form element (may be empty)
	 */
	List<?> getOptions();
}