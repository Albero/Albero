package nl.profict.albero.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A default {@link Context context} implementation.
 *
 * @author levi_h
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