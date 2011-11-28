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
package nl.trivento.albero.model.questions;

import nl.trivento.albero.model.Context;
import nl.trivento.albero.model.builders.FormBuilder;
import nl.trivento.albero.model.text.Dictionary;
import nl.trivento.albero.model.text.TextContainer;
import nl.trivento.albero.model.validation.ValidationResults;

/**
 * A question. The given answer influences the results of a tree.
 *
 * @see QuestionNode
 */
public interface Question extends TextContainer {
	/**
	 * Determines whether this question is answered in a certain context.
	 *
	 * @param context the context to use
	 * @return {@code true} if this question is answered in the given context, {@code false} otherwise
	 */
	boolean isAnswered(Context context);

	/**
	 * Validates the answers to this question in a certain context.
	 *
	 * @param context the context to validate
	 * @param dictionary the dictionary to use for validation messages
	 * @param validationResults the validation results to update
	 */
	void validate(Context context, Dictionary dictionary, ValidationResults validationResults);

	/**
	 * Asks this question.
	 *
	 * @param context the context in which the question should be asked
	 * @param validate whether to validate the given context
	 * @param dictionary the dictionary to use for form texts
	 * @param formBuilder the form builder to use
	 */
	void ask(Context context, boolean validate, Dictionary dictionary, FormBuilder formBuilder);
}
