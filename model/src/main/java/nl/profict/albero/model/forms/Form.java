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
package nl.profict.albero.model.forms;

import java.util.List;

import nl.profict.albero.model.text.TextContainer;

/**
 * An input form. It can contain either subforms or {@link FormElement form elements}.
 *
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
