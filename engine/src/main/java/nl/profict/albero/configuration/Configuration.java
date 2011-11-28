package nl.profict.albero.configuration;

import java.util.Map;
import java.util.Set;

import nl.profict.albero.Engine;
import nl.profict.albero.extensions.ExtensionProvider;
import nl.profict.albero.parsers.Parser;
import nl.profict.albero.repositories.Repository;
import nl.profict.albero.traversal.TraversalStrategy;

/**
 * The configuration of an {@link Engine engine}. A configuration consists of {@link ConfigurationElement configuration
 * elements}, which are {@link ConfigurationElement#initialise(Configuration, Map) initialised} upon creation.
 *
 * @author levi_h
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