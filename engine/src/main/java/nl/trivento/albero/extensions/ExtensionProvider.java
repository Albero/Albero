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
package nl.trivento.albero.extensions;

import java.util.Set;

import nl.trivento.albero.configuration.ConfigurationElement;
import nl.trivento.albero.model.external.ExternalValueProvider;

/**
 * Provides extensions that can be used by tree authors.
 *
 */
public interface ExtensionProvider extends ConfigurationElement {
	/**
	 * Returns elements that allow tree authors to obtain values from external resources.
	 *
	 * @return a set of external value providers (may be empty, but not {@code null})
	 */
	Set<ExternalValueProvider> getExternalValueProviders();
}
