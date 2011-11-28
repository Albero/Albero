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
package nl.profict.albero.model;

import java.util.Set;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SuppressWarnings("all")
@Test
public class DefaultContextTest {
	private Context context;

	@BeforeMethod
	public void createContext() {
		context = new DefaultContext();
	}

	public void initialContextShouldBeEmpty() {
		Set<String> variableTypes = context.getVariableTypes();
		assert variableTypes != null;
		assert variableTypes.isEmpty();
	}

	public void variableTypesShouldContainNameOfNewVariable() {
		context.setVariable("information", "var", "value");

		Set<String> variableTypes = context.getVariableTypes();
		assert variableTypes != null;
		assert variableTypes.size() == 1;
		assert variableTypes.contains("information");
	}

	public void variableNamesOfNonexistentTypeShouldBeEmpty() {
		Set<String> variableNames = context.getVariableNames("nonexistent");
		assert variableNames != null;
		assert variableNames.isEmpty();
	}

	public void variableNamesShouldContainNameOfNewVariable() {
		context.setVariable("information", "var", "value");

		Set<String> variableNames = context.getVariableNames("information");
		assert variableNames != null;
		assert variableNames.size() == 1;
		assert variableNames.contains("var");
	}

	public void newVariableShouldBeObtainable() {
		context.setVariable("information", "var", "value");

		Object variable = context.getVariable("information", "var");
		assert variable != null;
		assert variable.equals("value");
	}

	@Test(expectedExceptions = ContextException.class)
	public void obtainingVariableWithNonexistentTypeShouldCauseException() {
		context.getVariable("information", "nonexistent");
	}

	@Test(expectedExceptions = ContextException.class)
	public void obtainingNonexistentVariableShouldCauseException() {
		context.setVariable("information", "var", "value");

		context.getVariable("information", "nonexistent");
	}

	public void variableShouldBeRemovable() {
		context.setVariable("information", "var", "value");
		context.removeVariable("information", "var");

		assert !context.getVariableNames("information").contains("var");
	}

	@Test(expectedExceptions = ContextException.class)
	public void removingNonexistentVariableShouldCauseException() {
		context.removeVariable("information", "nonexistent");
	}

	public void settingVariableTwiceShouldReplaceIt() {
		context.setVariable("information", "var", "val");
		context.setVariable("information", "var", "value");

		Object variable = context.getVariable("information", "var");
		assert variable != null;
		assert variable.equals("value");
	}

	public void revisitVariableShouldNotBeEncodable() {
		assert !context.shouldBeEncoded("albero", "revisit");
	}

	public void resultVariablesShouldNotBeDecodable() {
		assert !context.shouldBeDecoded("results", "var");
		assert !context.shouldBeDecoded("results", "other_var");
	}
}
