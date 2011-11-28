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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import nl.profict.albero.model.Context;
import nl.profict.albero.model.text.Dictionary;

/**
 * Convenience superclass of {@link Question questions}.
 *
 * @author levi_h
 */
public abstract class AbstractQuestion implements Question {
	private Map<String, String> texts;

	/**
	 * Creates an abstract question.
	 */
	protected AbstractQuestion() {
		texts = new HashMap<String, String>();
	}

	/**
	 * Adds a text to this question.
	 *
	 * @param type the type of the text to add
	 * @param text the text to add
	 */
	public void addText(String type, String text) {
		texts.put(type, text);
	}

	public Map<String, String> getTexts() {
		return Collections.unmodifiableMap(texts);
	}

	/**
	 * Translates the texts of this question, using the values in a certain context as parameters.
	 *
	 * @param context the context that contains the role and the parameters
	 * @param dictionary the dictionary to use
	 * @return the translations of the texts in this question
	 */
	protected Map<String, String> translateTexts(Context context, Dictionary dictionary) {
		Map<String, String> translatedTexts = new HashMap<String, String>();

		String role = (String) context.getVariable(Context.ALBERO, "role");

		Map<String, Object> parameters = new HashMap<String, Object>();

		for (String type: context.getVariableTypes()) {
			if (!type.equals(Context.ALBERO)) {
				for (String name: context.getVariableNames(type)) {
					parameters.put(String.format("%s.%s", type, name), context.getVariable(type, name));
				}
			}
		}

		for (Map.Entry<String, String> text: texts.entrySet()) {
			translatedTexts.put(text.getKey(), dictionary.findTranslation(role, text.getValue(), parameters));
		}

		return translatedTexts;
	}
}
