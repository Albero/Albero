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
package nl.trivento.albero.configuration;

import java.util.Map;

/**
 * A part of a {@link Configuration configuration}.
 *
 */
public interface ConfigurationElement {
	/**
	 * Initialises this configuration element.
	 *
	 * @param configuration the configuration that contains this configuration element
	 * @param parameters the configuration parameters to use
	 * @throws ConfigurationException when this configuration element can't be initialised
	 */
	void initialise(Configuration configuration, Map<String, String> parameters) throws ConfigurationException;

	/**
	 * Destroys this configuration element.
	 *
	 * @throws ConfigurationException when this configuration element can't be destroyed
	 */
	void destroy() throws ConfigurationException;
}
