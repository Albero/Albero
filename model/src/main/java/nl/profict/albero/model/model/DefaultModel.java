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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A default {@link Model model} implementation.
 *
 */
public class DefaultModel implements Model {
	private List<Property> properties;

	/**
	 * Creates a default model.
	 */
	public DefaultModel() {
		properties = new LinkedList<Property>();
	}

	/**
	 * Adds a property to this model.
	 *
	 * @param property the property to add
	 */
	public void addProperty(Property property) {
		properties.add(property);
	}

	/**
	 * Determines whether this model contains a property with a certain path.
	 *
	 * @param path the path to the property
	 * @return {@code true} if this model contains a property with the given path or {@code false} if it doesn't
	 */
	public boolean hasProperty(String path) {
		return getProperty(path, false) != null;
	}

	/**
	 * Finds a property.
	 *
	 * @param path the path to the property
	 * @return the property with the given path or {@code null} if the property could not be found
	 * @throws ModelException when the property with the given path can't be found
	 */
	public Property getProperty(String path) throws ModelException {
		return getProperty(path, true);
	}

	private Property getProperty(String path, boolean required) {
		return getProperty(properties, path.split("\\."), 0, required);
	}

	private Property getProperty(
			List<Property> properties, String[] path, int index, boolean required) throws ModelException {
		Iterator<Property> it = properties.iterator();
		Property property = null;

		while (it.hasNext() && (property == null)) {
			property = it.next();

			if (!property.getName().equals(path[index])) {
				property = null;
			}
		}

		if (property == null) {
			StringBuilder propertyBuilder = new StringBuilder();

			for (int i = 0; i <= index; i++) {
				if (i > 0) {
					propertyBuilder.append('.');
				}

				propertyBuilder.append(properties.get(i));
			}

			throw new ModelException("can't find property '", propertyBuilder, "'");
		} else if (index + 1 < path.length) {
			property = getProperty(property.getType().getSubproperties(), path, index + 1, required);
		}

		return property;
	}

	@Override
	public String toString() {
		return String.format("default model (properties: %s)", properties);
	}
}
