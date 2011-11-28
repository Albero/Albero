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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A property type that contains a number of subproperties.
 *
 */
public class CompoundPropertyType implements PropertyType {
	private String name;

	private List<Property> subproperties;

	/**
	 * Creates a compound property type.
	 *
	 * @param name the name of the property type
	 */
	public CompoundPropertyType(String name) {
		this.name = name;

		subproperties = new ArrayList<Property>();
	}

	public String getName() {
		return name;
	}

	/**
	 * Adds a property to this property type.
	 *
	 * @param property the property to add
	 */
	public void addProperty(Property property) {
		subproperties.add(property);
	}

	public List<Property> getSubproperties() {
		return Collections.unmodifiableList(subproperties);
	}

	@Override
	public String toString() {
		return String.format("compound property type (name: '%s', subproperties: %s)", name, subproperties);
	}
}
