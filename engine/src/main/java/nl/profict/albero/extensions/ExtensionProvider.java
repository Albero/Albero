package nl.profict.albero.extensions;

import java.util.Set;

import nl.profict.albero.configuration.ConfigurationElement;
import nl.profict.albero.model.external.ExternalValueProvider;

/**
 * Provides extensions that can be used by tree authors.
 *
 * @author levi_h
 */
public interface ExtensionProvider extends ConfigurationElement {
	/**
	 * Returns elements that allow tree authors to obtain values from external resources.
	 *
	 * @return a set of external value providers (may be empty, but not {@code null})
	 */
	Set<ExternalValueProvider> getExternalValueProviders();
}