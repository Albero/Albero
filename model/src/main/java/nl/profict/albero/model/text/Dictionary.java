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

import java.util.Map;

/**
 * A collection of translations. Each translation is identified by two properties: a <i>role</i> and a <i>key</i>. The
 * role can be a language, but might also be more specific.
 * <p>
 * It's possible to supply named parameters when asking for a translation. Each of the parameter names that occur within
 * braces in the translation will be replaced with the parameter value. So if a parameter named {@code value} is given
 * with the value {@code ten}, then the translation &quot;{value} is not a number&quot; will result in the text
 * &quot;ten is not a number&quot;.
 *
 */
public interface Dictionary {
	/**
	 * Determines whether this dictionary contains a translation for a certain role/key combination.
	 *
	 * @param role the role of the translation to check
	 * @param key the key of the translation to check
	 * @return {@code true} if this dictionary has a translation for the given role and key, {@code false} if it doesn't
	 */
	boolean hasTranslation(String role, String key);

	/**
	 * Finds a translation. If no translation was added for the given role/key combination, the key will be returned.
	 * No interpolation will take place.
	 *
	 * @param role the role of the translation to find
	 * @param key the key of the translation to find
	 * @return the translation for the given role and key or the key if no translation is available for the role/key
	 *         combination
	 */
	String findTranslation(String role, String key);

	/**
	 * Finds a translation and interpolates it with parameters. If no translation can be found, the key is used.
	 *
	 * @param role the role of the translation to find
	 * @param key the key of the translation to find
	 * @param parameters the parameters to replace if they occur within braces in the found translation
	 * @return the translation for the given role and key or the key if no translation is available for the role/key
	 *         combination, interpolated with the given parameters
	 */
	String findTranslation(String role, String key, Map<String, ?> parameters);
}
