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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nl.trivento.albero.model.Context;
import nl.trivento.albero.model.builders.FormBuilder;
import nl.trivento.albero.model.text.Dictionary;
import nl.trivento.albero.model.validation.ValidationResults;

/**
 * A question that is made up of other questions.
 *
 */
public class CompoundQuestion extends AbstractQuestion {
	private List<Question> questions;

	/**
	 * Creates a compound question.
	 */
	public CompoundQuestion() {
		questions = new ArrayList<Question>();
	}

	/**
	 * Adds a question to this compound question.
	 *
	 * @param question the question to add
	 */
	public void addQuestion(Question question) {
		questions.add(question);
	}

	public boolean isAnswered(Context context) {
		boolean answered = true;

		Iterator<Question> it = questions.iterator();

		while (answered && it.hasNext()) {
			answered = it.next().isAnswered(context);
		}

		return answered;
	}

	public void validate(Context context, Dictionary dictionary, ValidationResults validationResults) {
		for (Question question: questions) {
			question.validate(context, dictionary, validationResults);
		}
	}

	public void ask(Context context, boolean validate, Dictionary dictionary, FormBuilder formBuilder) {
		FormBuilder subformBuilder = new FormBuilder();

		for (Map.Entry<String, String> text: translateTexts(context, dictionary).entrySet()) {
			subformBuilder.addText(text.getKey(), text.getValue());
		}

		for (Question question: questions) {
			question.ask(context, validate, dictionary, subformBuilder);
		}

		formBuilder.addForm(subformBuilder.build());
	}

	@Override
	public String toString() {
		return String.format("compound question (texts: %s, questions: %s)", getTexts(), questions);
	}
}
