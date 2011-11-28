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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A default {@link Context context} implementation.
 *
 */
public class DefaultContext implements Context {
	private Map<String, Map<String, Object>> variables;

	/**
	 * Creates a default context.
	 */
	public DefaultContext() {
		variables = new HashMap<String, Map<String, Object>>();
	}

	public Set<String> getVariableTypes() {
		return Collections.unmodifiableSet(variables.keySet());
	}

	public Set<String> getVariableNames(String type) {
		return Collections.unmodifiableSet(getVariables(type, false, false).keySet());
	}

	public Object getVariable(String type, String name) throws ContextException {
		Map<String, Object> variables = getVariables(type, true, false);

		if (!variables.containsKey(name)) {
			throw new ContextException("can't find variable with type '", type, "' and name '", name, "'");
		}

		return variables.get(name);
	}

	public void setVariable(String type, String name, Object value) {
		getVariables(type, true, true).put(name, value);
	}

	public void removeVariable(String type, String name) throws ContextException {
		Map<String, Object> variables = getVariables(type, true, false);

		if (!variables.containsKey(name)) {
			throw new ContextException("can't find variable with type '", type, "' and name '", name, "'");
		}

		variables.remove(name);
	}

	private Map<String, Object> getVariables(String type, boolean require, boolean create) throws ContextException {
		Map<String, Object> variables;

		if (this.variables.containsKey(type)) {
			variables = this.variables.get(type);
		} else {
			variables = new HashMap<String, Object>();

			if (require) {
				if (create) {
					this.variables.put(type, variables);
				} else {
					throw new ContextException("can't find variable type '", type, "'");
				}
			}
		}

		return variables;
	}

	public boolean shouldBeEncoded(String type, String name) {
		return !(type.equals(Context.ALBERO) && name.equals("revisit"));
	}

	public boolean shouldBeDecoded(String type, String name) {
		return !type.equals(Context.RESULTS);
	}

	@Override
	public String toString() {
		return String.format("default context (variables: %s)", variables);
	}
}
