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
package nl.profict.albero;

import static nl.profict.albero.configuration.DefaultConfiguration.PARSER_CLASSES;
import static nl.profict.albero.configuration.DefaultConfiguration.REPOSITORY_CLASS;
import static nl.profict.albero.configuration.DefaultConfiguration.TRAVERSAL_STRATEGY_CLASSES;
import static nl.profict.albero.model.Context.ALBERO;
import static nl.profict.albero.model.Context.INFORMATION;
import static nl.profict.albero.model.Context.RESULTS;

import java.util.HashMap;
import java.util.Map;

import nl.profict.albero.configuration.DefaultConfiguration;
import nl.profict.albero.model.Context;
import nl.profict.albero.model.DefaultContext;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SuppressWarnings("all")
@Test
public class EngineTest {
	private Engine engine;

	private Context context;

	@BeforeMethod
	public void createEngineAndContext() {
		Map<String, String> configurationParameters = new HashMap<String, String>();
		configurationParameters.put(REPOSITORY_CLASS, "nl.profict.albero.repositories.CodeRepository");
		configurationParameters.put(PARSER_CLASSES, "nl.profict.albero.parsers.CodeParser");
		configurationParameters.put(TRAVERSAL_STRATEGY_CLASSES,
			"nl.profict.albero.traversal.FirstEvaluatableNodePickingTraversalStrategy");

		engine = new Engine(new DefaultConfiguration(configurationParameters));

		context = new DefaultContext();
		context.setVariable(ALBERO, "tree", "discount");
		context.setVariable(ALBERO, "role", "en");
		context.setVariable(ALBERO, "traversal_strategy", "first evaluatable node picker");
	}

	@Test(expectedExceptions = AlberoException.class)
	public void evaluatingContextWithoutTreeShouldCauseException() {
		Context context = new DefaultContext();
		context.setVariable(ALBERO, "role", "en");
		context.setVariable(ALBERO, "traversal_strategy", "first evaluatable node picker");

		engine.evaluate(context);
	}

	@Test(expectedExceptions = AlberoException.class)
	public void evaluatingContextWithoutRoleShouldCauseException() {
		Context context = new DefaultContext();
		context.setVariable(ALBERO, "tree", "discount");
		context.setVariable(ALBERO, "traversal_strategy", "first evaluatable node picker");

		engine.evaluate(context);
	}

	@Test(expectedExceptions = AlberoException.class)
	public void evaluatingContextWithoutTraversalStrategyShouldCauseException() {
		Context context = new DefaultContext();
		context.setVariable(ALBERO, "tree", "discount");
		context.setVariable(ALBERO, "role", "en");

		engine.evaluate(context);
	}

	public void evaluatingContextWithoutNodeShouldResultInNodePickedByTraversalStrategy() {
		engine.evaluate(context);

		Object node = context.getVariable(ALBERO, "node");
		assert node != null;
		assert node.equals("first_visit");
	}

	public void evaluatingContextWithoutNodeShouldResultInForm() {
		assert engine.evaluate(context) != null;
	}

	public void evaluatingContextWithAnsweredQuestionShouldResultInOtherNodePickedByTraversalStrategy() {
		context.setVariable(ALBERO, "node", "first_visit");
		context.setVariable(INFORMATION, "first_visit", true);

		engine.evaluate(context);

		Object node = context.getVariable(ALBERO, "node");
		assert node != null;
		assert node.equals("items_bought");
	}

	public void evaluatingContextWithAnsweredQuestionsShouldNotResultInForm() {
		context.setVariable(INFORMATION, "first_visit", true);

		context.setVariable(ALBERO, "node", "items_bought");
		context.setVariable(INFORMATION, "items_bought", 7);

		assert engine.evaluate(context) == null;
	}

	public void evaluatingContextWithAnsweredQuestionsShouldResultInResults() {
		context.setVariable(INFORMATION, "first_visit", true);

		context.setVariable(ALBERO, "node", "items_bought");
		context.setVariable(INFORMATION, "items_bought", 7);

		engine.evaluate(context);

		assert context.getVariableNames(RESULTS).contains("discount");

		Object discount = context.getVariable(RESULTS, "discount");
		assert discount instanceof Integer;
		assert ((Integer) discount).intValue() == 10;
	}
}
