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
package nl.profict.albero.utilities;

import java.util.Map;

/**
 * Contains utility methods related to strings.
 *
 */
public class StringUtilities {
	private StringUtilities() {}

	/**
	 * Joins the string representation of a number of objects.
	 *
	 * @param parts the parts to join
	 * @return a concatenation of the textual rendition of the parts
	 */
	public static String join(Object... parts) {
		StringBuilder builder = new StringBuilder();

		for (Object part: parts) {
			builder.append(part);
		}

		return builder.toString();
	}

	/**
	 * Replaces parameters (within braces) in a text.
	 *
	 * @param text the text to interpolate
	 * @param parameters the parameters to use
	 * @return the given text, interpolated with the given parameters
	 */
	public static String interpolate(String text, Map<String, ?> parameters) {
		String interpolatedText = text;

		do {
			text = interpolatedText;

			for (String parameterName: parameters.keySet()) {
				String target = String.format("{%s}", parameterName);
				String replacement = String.valueOf(parameters.get(parameterName));

				interpolatedText = interpolatedText.replace(target, replacement);
			}
		} while (!interpolatedText.equals(text));

		return interpolatedText;
	}
}
