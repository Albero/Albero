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
package nl.trivento.albero.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Convenience superclass of {@link Node node} implementations.
 *
 */
public abstract class AbstractNode implements Node {
	private String code;
	private String group;

	private Map<String, Object> dynamicProperties;

	private List<NodeCondition> conditions;

	/**
	 * Creates an abstract node.
	 *
	 * @param code the code of the node
	 * @param group the group of the node
	 */
	public AbstractNode(String code, String group) {
		this.code = code;
		this.group = group;

		dynamicProperties = new HashMap<String, Object>();

		conditions = new ArrayList<NodeCondition>();
	}

	public String getCode() {
		return code;
	}

	public String getGroup() {
		return group;
	}

	public Map<String, ?> getDynamicProperties() {
		return Collections.unmodifiableMap(dynamicProperties);
	}

	/**
	 * Adds or replaces a dynamic property.
	 *
	 * @param name the name of the dynamic property to add; if a dynamic property with the name already exists, it will
	 *             be overwritten
	 * @param value the value of the dynamic property
	 */
	public void setDynamicProperty(String name, Object value) {
		dynamicProperties.put(name, value);
	}

	/**
	 * Adds a condition to the list of {@link NodeCondition node conditions}.
	 *
	 * @param condition the condition to add
	 */
	public void addCondition(NodeCondition condition) {
		conditions.add(condition);
	}

	public boolean isEvaluatable(Context context) {
		boolean evaluatable = true;

		Iterator<NodeCondition> it = conditions.iterator();

		while (it.hasNext() && evaluatable) {
			evaluatable &= it.next().applies(context);
		}

		return evaluatable;
	}
}
