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
package nl.trivento.albero.model.external;

import java.util.Map;

/**
 * Provides a value from a source outside the model.
 * <p>
 * It is possible that the provision depends on some parameters; in that case, implementations are required to document
 * that.
 *
 */
public interface ExternalValueProvider {
	/**
	 * Returns the name of this external value provider. It should be unique.
	 *
	 * @return this external value provider's name
	 */
	String getName();

	/**
	 * Retrieves an external value, based on a number of parameters.
	 *
	 * @param parameters parameters for the provision
	 * @return the external value
	 */
	Object provideValue(Map<String, ?> parameters);
}
