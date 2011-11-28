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
package nl.profict.albero.model.text;

import java.util.Collections;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SuppressWarnings("all")
@Test
public class DefaultDictionaryTest {
	private DefaultDictionary dictionary;

	@BeforeMethod
	public void createDictionary() {
		dictionary = new DefaultDictionary();
	}

	public void translationThatWasNotAddedShouldNotBeAvailable() {
		assert !dictionary.hasTranslation("en", "model.name");
	}

	public void addedTranslationShouldBeAvailable() {
		dictionary.addTranslation("en", "model.age", "Age");

		assert dictionary.hasTranslation("en", "model.age");
	}

	public void addedTranslationShouldBeFindable() {
		dictionary.addTranslation("en", "model.name", "Name");

		String translation = dictionary.findTranslation("en", "model.name");
		assert translation != null;
		assert translation.equals("Name");
	}

	public void addingExistingTranslationShouldReplaceExistingTranslation() {
		dictionary.addTranslation("en", "model.age", "Aeg");
		dictionary.addTranslation("en", "model.age", "Age");

		String translation = dictionary.findTranslation("en", "model.age");
		assert translation != null;
		assert translation.equals("Age");
	}

	public void findingTranslationForUnknownRoleAndKeyShouldResultInKey() {
		String translation = dictionary.findTranslation("en", "Name");
		assert translation != null;
		assert translation.equals("Name");
	}

	public void parametersShouldBeInterpolated() {
		dictionary.addTranslation("en", "validation.not_a_number.message", "{value} is not a number");

		String translation = dictionary.findTranslation(
			"en", "validation.not_a_number.message", Collections.singletonMap("value", "ten"));
		assert translation != null;
		assert translation.equals("ten is not a number");
	}
}
