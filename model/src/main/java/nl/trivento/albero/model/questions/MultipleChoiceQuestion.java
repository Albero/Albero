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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.trivento.albero.model.Context;
import nl.trivento.albero.model.builders.FormElementBuilder;
import nl.trivento.albero.model.model.Model;
import nl.trivento.albero.model.text.Dictionary;

/**
 * A question with one or more answers that are selected from a certain set of options.
 *
 */
public class MultipleChoiceQuestion extends SimpleQuestion {
	private List<?> options;

	/**
	 * Creates a multiple choice question.
	 *
	 * @param options the possible answers
	 * @param model the model that contains the property
	 * @param propertyPath the path to the property
	 */
	public MultipleChoiceQuestion(List<?> options, Model model, String propertyPath) {
		super(model, propertyPath);

		this.options = convertOptions(options, model.getProperty(propertyPath).getType().getName());
	}

	private List<?> convertOptions(List<?> options, String propertyType) {
		List<Object> convertedOptions = new ArrayList<Object>();

		for (Object option: options) {
			Object convertedOption;

			if (propertyType.equals("number") && (option instanceof Number)) {
				convertedOption = ((Number) option).longValue();
			} else {
				convertedOption = option;
			}

			convertedOptions.add(convertedOption);
		}

		return convertedOptions;
	}

	@Override
	public boolean isAnswered(Context context) {
		return super.isAnswered(context) && isValidOption(context);
	}

	private boolean isValidOption(Context context) {
		Object answer = context.getVariable(Context.INFORMATION, getPropertyPath());

		return options.containsAll(getProperty().isList() ? (List<?>) answer : Collections.singletonList(answer));
	}

	@Override
	protected void fillElementBuilder(FormElementBuilder elementBuilder, Context context, Dictionary dictionary) {
		super.fillElementBuilder(elementBuilder, context, dictionary);

		for (Object option: options) {
			elementBuilder.addOption(option);
		}
	}
}
