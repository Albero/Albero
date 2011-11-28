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
