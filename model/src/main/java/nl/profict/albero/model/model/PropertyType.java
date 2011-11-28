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

import java.util.List;

/**
 * The type of a {@link Property property}.
 *
 */
public interface PropertyType {
	/**
	 * Returns the name of this property type.
	 *
	 * @return this property type's name
	 */
	String getName();

	/**
	 * Returns all of the subproperties of this property type.
	 *
	 * @return this property type's child properties
	 */
	List<Property> getSubproperties();
}
