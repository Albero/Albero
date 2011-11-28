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

import nl.trivento.albero.model.AbstractNode;
import nl.trivento.albero.model.Context;
import nl.trivento.albero.model.EvaluationContext;
import nl.trivento.albero.model.EvaluationException;
import nl.trivento.albero.model.builders.BuilderException;
import nl.trivento.albero.model.builders.FormBuilder;
import nl.trivento.albero.model.forms.Form;
import nl.trivento.albero.model.text.Dictionary;
import nl.trivento.albero.model.validation.ValidationResults;

/**
 * A node that needs a {@link Question question} to be answered.
 *
 */
public class QuestionNode extends AbstractNode {
	private Question question;

	/**
	 * Creates a question node.
	 *
	 * @param code the code for the node
	 * @param group the group for the node
	 * @param question the question asked
	 */
	public QuestionNode(String code, String group, Question question) {
		super(code, group);

		this.question = question;
 	}

	@Override
	public boolean isEvaluatable(Context context) {
		return super.isEvaluatable(context) && !question.isAnswered(context);
	}

	public Form evaluate(EvaluationContext evaluationContext, boolean validate) throws EvaluationException {
		Form form;

		Context context = evaluationContext.getContext();
		Dictionary dictionary = evaluationContext.getDictionary();

		if (question.isAnswered(context) && (!validate || isQuestionValid(context, dictionary))) {
			form = null;
		} else {
			try {
				FormBuilder formBuilder = new FormBuilder();
				question.ask(context, validate, dictionary, formBuilder);
				form = formBuilder.build();
			} catch (BuilderException exception) {
				throw new EvaluationException(exception, "can't build evaluation");
			}
		}

		return form;
	}

	private boolean isQuestionValid(Context context, Dictionary dictionary) {
		ValidationResults validationResults = new ValidationResults();
		question.validate(context, dictionary, validationResults);
		return !validationResults.hasErrors();
	}

	@Override
	public String toString() {
		return String.format("question node (question: %s)", question);
	}
}
