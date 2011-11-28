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
package nl.profict.albero.model.questions;

import nl.profict.albero.model.Context;
import nl.profict.albero.model.DefaultContext;
import nl.profict.albero.model.Node;
import nl.profict.albero.model.builders.EvaluationContextBuilder;
import nl.profict.albero.model.model.DefaultModel;
import nl.profict.albero.model.model.Property;
import nl.profict.albero.model.model.SimplePropertyType;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SuppressWarnings("all")
@Test
public class QuestionNodeTest {
	private Node questionNode;

	@BeforeMethod
	private void createQuestion() {
		DefaultModel model = new DefaultModel();
		model.addProperty(new Property("age", new SimplePropertyType("number")));

		questionNode = new QuestionNode("age", null, new SimpleQuestion(model, "age"));
	}

	public void questionNodeShouldHaveCode() {
		String code = questionNode.getCode();
		assert code != null;
		assert code.equals("age");
	}

	public void unansweredQuestionShouldBeEvaluatable() {
		Context context = new DefaultContext();
		context.setVariable(Context.ALBERO, "role", "en");

		assert questionNode.isEvaluatable(context);
	}

	public void evaluatingQuestionNodeWithoutAnsweringShouldResultInForm() {
		Context context = new DefaultContext();
		context.setVariable(Context.ALBERO, "role", "en");

		assert questionNode.evaluate(new EvaluationContextBuilder().setContext(context).build(), false) != null;
	}

	public void answeredQuestionShouldNotBeEvaluatable() {
		Context context = new DefaultContext();
		context.setVariable(Context.ALBERO, "role", "en");
		context.setVariable(Context.INFORMATION, "age", 27);

		assert !questionNode.isEvaluatable(context);
	}

	public void evaluationQuestionNodeWithPossibleAnswerShouldResultInNull() {
		DefaultContext context = new DefaultContext();
		context.setVariable(Context.ALBERO, "role", "en");
		context.setVariable(Context.INFORMATION, "age", 27);

		assert questionNode.evaluate(new EvaluationContextBuilder().setContext(context).build(), false) == null;
	}
}
