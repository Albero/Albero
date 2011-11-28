/* Copyright 2011-2012 Profict Holding 
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package nl.trivento.albero.model.forms;

import java.util.List;

import nl.trivento.albero.model.text.TextContainer;

/**
 * An element in a {@link Form form}.
 *
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
