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
package nl.profict.albero.model.model;

import nl.profict.albero.model.Tree;

/**
 * The model of a {@link Tree tree}.
 *
 * @author levi_h
 */
public interface Model {
	/**
	 * Determines whether this model contains a property with a certain path.
	 *
	 * @param path the path to the property
	 * @return {@code true} if this model contains a property with the given path or {@code false} if it doesn't
	 */
	boolean hasProperty(String path);

	/**
	 * Finds a property.
	 *
	 * @param path the path to the property
	 * @return the property with the given path or {@code null} if the property could not be found
	 * @throws ModelException when the property with the given path can't be found
	 */
	Property getProperty(String path) throws ModelException;
}
