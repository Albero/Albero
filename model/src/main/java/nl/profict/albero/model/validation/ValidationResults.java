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
import java.util.LinkedList;
import java.util.List;

import nl.profict.albero.model.Context;

/**
 * The results of validating a {@link Context context}.
 *
 */
public class ValidationResults {
	private List<ValidationError> errors;

	/**
	 * Creates validation results.
	 */
	public ValidationResults() {
		errors = new LinkedList<ValidationError>();
	}

	/**
	 * Adds a validation error.
	 *
	 * @param error the validation error to add
	 */
	public void addError(ValidationError error) {
		errors.add(error);
	}

	/**
	 * Determines whether these validation results contains errors.
	 *
	 * @return {@code true} if any valudation errors have been added, {@code false} otherwise
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	/**
	 * Returns all added validation errors.
	 *
	 * @return a list with all validation errors
	 */
	public List<ValidationError> getErrors() {
		return Collections.unmodifiableList(errors);
	}

	@Override
	public String toString() {
		return String.format("validation results (errors: %s)", errors);
	}
}
