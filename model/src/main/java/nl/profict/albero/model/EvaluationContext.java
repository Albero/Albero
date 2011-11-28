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
package nl.profict.albero.model;

import nl.profict.albero.model.external.ExternalValueProvider;
import nl.profict.albero.model.text.Dictionary;

/**
 * The context in which {@link Node nodes} and {@link ResultProvider result providers} are evaluated.
 *
 * @author wanja krah
 */
public interface EvaluationContext {
	/**
	 * Returns the state of the evaluated tree.
	 *
	 * @return the current context
	 */
	Context getContext();

	/**
	 * Returns the dictionary of the evaluated tree.
	 *
	 * @return the dictionary to use
	 */
	Dictionary getDictionary();

	/**
	 * Finds an external value provider by its name.
	 *
	 * @param name the name of the required external value provider
	 * @return the external value provider with the given name
	 * @throws EvaluationException when no external value provider can be found with the given name
	 */
	ExternalValueProvider findExternalValueProvider(String name) throws EvaluationException;
}
