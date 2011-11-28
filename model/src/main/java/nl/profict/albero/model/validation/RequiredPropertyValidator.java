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
package nl.profict.albero.model.validation;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import nl.profict.albero.model.Context;
import nl.profict.albero.model.model.Model;
import nl.profict.albero.model.text.Dictionary;

/**
 * Validates that a required property actually has a value.
 *
 * @author levi_h
 */
public class RequiredPropertyValidator implements PropertyValidator {
	/**
	 * Creates a required property validator.
	 */
	public RequiredPropertyValidator() {}

	public void validate(Context context, Model model, String propertyPath,
			Dictionary dictionary, ValidationResults validationResults) {
		Object value = context.getVariable(Context.INFORMATION, propertyPath);

		if ((value == null) || value.equals("") ||
				(model.getProperty(propertyPath).isList() && ((List<?>) value).isEmpty())) {
			String role = (String) context.getVariable(Context.ALBERO, "role");

			String propertyTranslation = dictionary.findTranslation(role, String.format("model.%s", propertyPath));
			Map<String, ?> parameters = Collections.singletonMap("property", propertyTranslation);

			validationResults.addError(new ValidationError(dictionary.findTranslation(role, ERROR_KEY, parameters)));
		}
	}

	/** The key that will be used for the validation error when validation fails. */
	public final static String ERROR_KEY = "validation.required";
}
