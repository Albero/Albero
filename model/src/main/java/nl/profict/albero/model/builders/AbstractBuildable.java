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
package nl.profict.albero.model.builders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Simplifies implementing the {@link Buildable buildable interface}.
 *
 */
abstract class AbstractBuildable implements Buildable {
	private Map<String, Property<?>> properties;

	/**
	 * Creates an abstract buildable object.
	 */
	public AbstractBuildable() {
		properties = new HashMap<String, Property<?>>();
	}

	/**
	 * Adds an optional property.
	 *
	 * @param <T> the type of the property
	 * @param name the property name
	 * @param type the property type
	 */
	protected <T> void addProperty(String name, Class<T> type) {
		addProperty(name, type, null);
	}

	/**
	 * Adds an optional property.
	 *
	 * @param <T> the type of the property
	 * @param name the property name
	 * @param type the property type
	 * @param defaultValue the default value of the property
	 */
	protected <T> void addProperty(String name, Class<T> type, T defaultValue) {
		addProperty(name, type, false, defaultValue);
	}

	/**
	 * Adds a required property.
	 *
	 * @param <T> the type of the property
	 * @param name the property name
	 * @param type the property type
	 */
	protected <T> void addRequiredProperty(String name, Class<T> type) {
		addRequiredProperty(name, type, null);
	}

	/**
	 * Adds a required property.
	 *
	 * @param <T> the type of the property
	 * @param name the property name
	 * @param type the property type
	 * @param defaultValue the default value of the property
	 */
	protected <T> void addRequiredProperty(String name, Class<T> type, T defaultValue) {
		addProperty(name, type, true, defaultValue);
	}

	/**
	 * Adds a list property.
	 *
	 * @param <E> the type of the list
	 * @param name the property name
	 * @param elementType the property element type
	 */
	protected <E> void addListProperty(String name, Class<E> elementType) {
		addProperty(name, List.class, false, new ArrayList<E>());
	}
	/**
	 * Adds a map property.
	 *
	 * @param <K> the type of the key
	 * @param <V> the type of the value
	 * @param name the property name
	 * @param keyType the property key type
	 * @param valueType the property value type
	 */
	protected <K, V> void addMapProperty(String name, Class<K> keyType, Class<V> valueType) {
		addProperty(name, Map.class, false, new HashMap<K, V>());
	}

	private <T> void addProperty(String name, Class<T> type, boolean required, T defaultValue) {
		properties.put(name, new Property<T>(type, required, defaultValue));
	}

	/**
	 * Returns the value of a property.
	 *
	 * @param <T> the type of the property
	 * @param name the property name
	 * @param type the property type
	 * @return the value of the property
	 * @throws BuilderException when no property with the given characteristics can be found
	 */
	public <T> T getValue(String name, Class<T> type) throws BuilderException {
		return getProperty(name, type).value;
	}

	/**
	 * Sets the value of a property.
	 *
	 * @param <T> the type of the property
	 * @param name the property name
	 * @param type the property type
	 * @param value the value for the property
	 * @throws BuilderException when no property with the given characteristics can be found
	 */
	public <T> void setValue(String name, Class<T> type, T value) throws BuilderException {
		getProperty(name, type).value = value;
	}

	/**
	 * Returns a list property.
	 *
	 * @param <E> the type of the elements
	 * @param name the property name
	 * @param elementType the property element type
	 * @return the list property
	 * @throws BuilderException when no list property with the given characteristics can be found
	 */
	public <E> List<E> getList(String name, Class<E> elementType) throws BuilderException {
		List<?> list = getValue(name, List.class);

		@SuppressWarnings("unchecked")
		List<E> typedList = (List<E>) list;

		return typedList;
	}

	/**
	 * Adds a value to a list property.
	 *
	 * @param <E> the type of the element
	 * @param name the property name
	 * @param elementType the property element type
	 * @param element the value to add
	 * @throws BuilderException when no list property with the given characteristics can be found
	 */
	public <E> void addValue(String name, Class<E> elementType, E element) throws BuilderException {
		getList(name, elementType).add(element);
	}

	/**
	 * Returns a map property
	 *
	 * @param <K> the type of the key
	 * @param <V> the type of the value
	 * @param name the property name
	 * @param keyType the property key type
	 * @param valueType the property value type
	 * @return the map property
	 * @throws BuilderException when no map property with the given characteristics can be found
	 */
	public <K, V> Map<K, V> getMap(String name, Class<K> keyType, Class<V> valueType) throws BuilderException {
		Map<?, ?> map = getValue(name, Map.class);

		@SuppressWarnings("unchecked")
		Map<K, V> typedMap = (Map<K, V>) map;

		return typedMap;
	}

	/**
	 * Adds a value to a map property.
	 *
	 * @param <K> the type of the key
	 * @param <V> the type of the value
	 * @param name the property name
	 * @param keyType the property key type
	 * @param valueType the property value type
	 * @param key the key of the value
	 * @param value the value to add
	 * @throws BuilderException when no map property with the given characteristics can be found
	 */
	public <K, V> void addValue(
			String name, Class<K> keyType, Class<V> valueType, K key, V value) throws BuilderException {
		getMap(name, keyType, valueType).put(key, value);
	}

	private <T> Property<T> getProperty(String name, Class<T> type) throws BuilderException {
		Property<?> property = properties.get(name);

		if (property == null) {
			throw new BuilderException("can't find property '", name, "'");
		} else if (property.type != type) {
			throw new BuilderException("property '", name, "' is of type ", property.type.getName(), ", ",
				"not of type ", type.getName());
		}

		@SuppressWarnings("unchecked")
		Property<T> typedProperty = (Property<T>) property;

		return typedProperty;
	}

	public void checkValues() throws BuilderException {
		Iterator<Map.Entry<String, Property<?>>> it = properties.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, Property<?>> namedProperty = it.next();

			String name = namedProperty.getKey();
			Property<?> property = namedProperty.getValue();

			if (property.required) {
				Object value = property.value;

				if ((property.type == List.class) && ((List<?>) value).isEmpty()) {
					throw new BuilderException("list '", name, "' should not be empty");
				} else if ((property.type == Map.class) && ((Map<?, ?>) value).isEmpty()) {
					throw new BuilderException("map '", name, "' should not be empty");
				} else if (value == null) {
					throw new BuilderException("property '", name, "' is required");
				}
			}
		}
	}

	private static class Property<T> {
		public Class<T> type;
		public boolean required;

		public T value;

		public Property(Class<T> type, boolean required, T value) {
			this.type = type;
			this.required = required;

			this.value = value;
		}
	}
}
