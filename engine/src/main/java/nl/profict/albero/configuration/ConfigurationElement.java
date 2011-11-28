package nl.profict.albero.configuration;

import java.util.Map;

/**
 * A part of a {@link Configuration configuration}.
 *
 * @author levi_h
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