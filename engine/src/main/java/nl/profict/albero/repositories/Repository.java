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
package nl.profict.albero.repositories;

import nl.profict.albero.configuration.ConfigurationElement;

/**
 * Stores information about trees.
 *
 */
public interface Repository extends ConfigurationElement {
	/**
	 * Finds information on a tree by its code.
	 *
	 * @param code the code of the tree
	 * @return the information that is stored about the tree
	 * @throws RepositoryException when the tree information can't be located
	 */
	TreeInformation locate(String code) throws RepositoryException;
}
