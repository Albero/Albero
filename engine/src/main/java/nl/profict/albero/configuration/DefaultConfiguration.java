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
package nl.profict.albero.configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nl.profict.albero.Engine;
import nl.profict.albero.extensions.ExtensionProvider;
import nl.profict.albero.parsers.Parser;
import nl.profict.albero.repositories.Repository;
import nl.profict.albero.traversal.TraversalStrategy;
import nl.profict.albero.utilities.Logger;

/**
 * A default {@link Configuration configuration} that requires configuration properties to find the repository,
 * the parsers, the traversal strategies, and the extension providers that can be used by the {@link Engine engine}. The
 * selfsame parameters are used to configure said configuration elements.
 *
 */
public class DefaultConfiguration implements Configuration {
	private Map<String, String> parameters;

	private boolean open;

	private Repository repository;
	private Map<String, Parser> parsers;
	private Map<String, TraversalStrategy> traversalStrategies;
	private Set<ExtensionProvider> extensionProviders;

	private final Logger logger;

	/**
	 * Creates a default configuration.
	 *
	 * @param parameters the configuration parameters to use
	 * @throws ConfigurationException when the configuration can't be created
	 */
	public DefaultConfiguration(Map<String, String> parameters) throws ConfigurationException {
		this.parameters = Collections.unmodifiableMap(parameters);

		logger = Logger.get(getClass());

		findRepository();
		findParsers();
		findTraversalStrategies();
		findExtensionProviders();

		open();
	}

	private void findRepository() throws ConfigurationException {
		if (parameters.containsKey(REPOSITORY_CLASS)) {
			repository = loadConfigurationElement(parameters.get(REPOSITORY_CLASS), Repository.class);

			logger.debug("loaded repository: ", repository);
		} else {
			throw new ConfigurationException("can't find required parameter '", REPOSITORY_CLASS, "'");
		}
	}

	private void findParsers() {
		parsers = new HashMap<String, Parser>();

		if (parameters.containsKey(PARSER_CLASSES)) {
			for (String parserClassName: parameters.get(PARSER_CLASSES).split(",\\s*")) {
				Parser parser = loadConfigurationElement(parserClassName, Parser.class);

				logger.debug("loaded parser: ", parser);

				parsers.put(parser.getName(), parser);
			}
		} else {
			throw new ConfigurationException("can't find required parameter '", PARSER_CLASSES, "'");
		}
	}

	private void findTraversalStrategies() {
		traversalStrategies = new HashMap<String, TraversalStrategy>();

		if (parameters.containsKey(PARSER_CLASSES)) {
			for (String traversalStrategyClassName: parameters.get(TRAVERSAL_STRATEGY_CLASSES).split(",\\s*")) {
				TraversalStrategy traversalStrategy =
					loadConfigurationElement(traversalStrategyClassName, TraversalStrategy.class);

				logger.debug("loaded traversal strategy: ", traversalStrategy);

				traversalStrategies.put(traversalStrategy.getName(), traversalStrategy);
			}
		} else {
			throw new ConfigurationException("can't find required parameter '", TRAVERSAL_STRATEGY_CLASSES, "'");
		}
	}

	private void findExtensionProviders() {
		extensionProviders = new HashSet<ExtensionProvider>();

		if (parameters.containsKey(EXTENSION_PROVIDER_CLASSES)) {
			String extensionProviderClassNames = parameters.get(EXTENSION_PROVIDER_CLASSES);

			for (String extensionProviderClassName: extensionProviderClassNames.split(",\\s*")) {
				ExtensionProvider extensionProvider =
					loadConfigurationElement(extensionProviderClassName, ExtensionProvider.class);

				logger.debug("loaded extension provider: ", extensionProvider);

				extensionProviders.add(extensionProvider);
			}
		}
	}

	private <T> T loadConfigurationElement(String className, Class<T> type) throws ConfigurationException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		Class<?> configurationElementType;

		try {
			configurationElementType = classLoader.loadClass(className);
		} catch (ClassNotFoundException exception) {
			throw new ConfigurationException(exception, "can't load configuration element type");
		}

		if (type.isAssignableFrom(configurationElementType)) {
			try {
				return type.cast(configurationElementType.newInstance());
			} catch (InstantiationException exception) {
				throw new ConfigurationException(exception, "can not instantiate ", configurationElementType.getName());
			} catch (IllegalAccessException exception) {
				throw new ConfigurationException(exception, "may not instantiate ", configurationElementType.getName());
			}
		} else {
			throw new ConfigurationException("configuration element type ", configurationElementType, " is not ",
				"assignable to type ", type.getName());
		}
	}

	public Repository getRepository() {
		checkOpen();

		return repository;
	}

	public Map<String, Parser> getParsers() {
		checkOpen();

		return Collections.unmodifiableMap(parsers);
	}

	public Map<String, TraversalStrategy> getTraversalStrategies() {
		checkOpen();

		return Collections.unmodifiableMap(traversalStrategies);
	}

	public Set<ExtensionProvider> getExtensionProviders() {
		checkOpen();

		return Collections.unmodifiableSet(extensionProviders);
	}

	private void open() throws ConfigurationException {
		repository.initialise(this, parameters);

		for (Parser parser: parsers.values()) {
			parser.initialise(this, parameters);
		}

		for (TraversalStrategy traversalStrategy: traversalStrategies.values()) {
			traversalStrategy.initialise(this, parameters);
		}

		for (ExtensionProvider extensionProvider: extensionProviders) {
			extensionProvider.initialise(this, parameters);
		}

		open = true;
	}

	public void close() throws ConfigurationException {
		repository.destroy();

		for (Parser parser: parsers.values()) {
			parser.destroy();
		}

		for (TraversalStrategy traversalStrategy: traversalStrategies.values()) {
			traversalStrategy.destroy();
		}

		for (ExtensionProvider extensionProvider: extensionProviders) {
			extensionProvider.destroy();
		}

		open = false;
	}

	private void checkOpen() throws ConfigurationException {
		if (!open) {
			throw new ConfigurationException("the configuration is closed");
		}
	}

	@Override
	public String toString() {
		return String.format("default configuration (repository: %s, parsers: %s, traversal strategies: %s)",
			repository, parsers, traversalStrategies);
	}

	/**
	 * The name of the configuration parameter that contains the fully qualified class name of the repository that
	 * should be used: {@value}.
	 */
	public final static String REPOSITORY_CLASS = "albero.configurations.default.repository_class";

	/**
	 * The name of the configuration parameter that contains the comma-separated fully qualified class names of the
	 * parsers that should be used: {@value}.
	 */
	public final static String PARSER_CLASSES = "albero.configurations.default.parser_classes";

	/**
	 * The name of the configuration parameter that contains the comma-separated fully qualified class names of the
	 * traversal strategies that should be used: {@value}.
	 */
	public final static String TRAVERSAL_STRATEGY_CLASSES = "albero.configurations.default.traversal_strategy_classes";

	/**
	 * The name of the configuration parameter that contains the comma-separated fully qualified class name of the
	 * extension provider classes that should be used: {@value}.
	 */
	public final static String EXTENSION_PROVIDER_CLASSES = "albero.configurations.default.extension_provider_classes";
}
