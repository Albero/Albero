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
import java.util.Set;

import nl.trivento.albero.Engine;
import nl.trivento.albero.extensions.ExtensionProvider;
import nl.trivento.albero.parsers.Parser;
import nl.trivento.albero.repositories.Repository;
import nl.trivento.albero.traversal.TraversalStrategy;

/**
 * The configuration of an {@link Engine engine}. A configuration consists of {@link ConfigurationElement configuration
 * elements}, which are {@link ConfigurationElement#initialise(Configuration, Map) initialised} upon creation.
 *
 */
public interface Configuration {
	/**
	 * Returns the repository that should be used by the engine.
	 *
	 * @return the repository to use
	 */
	Repository getRepository();

	/**
	 * Returns all parsers.
	 *
	 * @return all configured parsers, keyed by name
	 */
	Map<String, Parser> getParsers();

	/**
	 * Returns all traversal strategies.
	 *
	 * @return all configured traversal strategies, keyed by name
	 */
	Map<String, TraversalStrategy> getTraversalStrategies();

	/**
	 * Returns all extension providers.
	 *
	 * @return all configured extension providers
	 */
	Set<ExtensionProvider> getExtensionProviders();

	/**
	 * Closes this configuration. After a configuration has been closed, it can no longer be used - any attempt to do so
	 * will result in a {@link ConfigurationException configuration exception}.
	 *
	 * @throws ConfigurationException when something goes wrong when closing this configuration
	 */
	void close() throws ConfigurationException;
}
