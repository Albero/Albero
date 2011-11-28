package nl.profict.albero.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import nl.profict.albero.model.forms.Form;

/**
 * A node that sets a number of context variables (of type {@value Context#INFORMATION}) when it is evaluated. It does
 * not result in a form.
 *
 * @author levi_h
 */
public class ValuesNode extends AbstractNode {
	private Map<String, Object> values;

	/**
	 * Creates a values node.
	 *
	 * @param code the node code
	 * @param group the node group
	 * @param values the values that should be set when the node is evaluated
	 */
	public ValuesNode(String code, String group, Map<String, Object> values) {
		super(code, group);

		this.values = new HashMap<String, Object>(values);
	}

	@Override
	public boolean isEvaluatable(Context context) {
		return super.isEvaluatable(context) && !valuesAvailable(context);
	}

	private boolean valuesAvailable(Context context) {
		boolean available = context.getVariableTypes().contains(Context.INFORMATION);

		Iterator<String> it = values.keySet().iterator();

		while (available && it.hasNext()) {
			available = context.getVariableNames(Context.INFORMATION).contains(it.next());
		}

		return available;
	}

	public Form evaluate(EvaluationContext evaluationContext, boolean validate) {
		Context context = evaluationContext.getContext();

		for (String name: values.keySet()) {
			context.setVariable(Context.INFORMATION, name, values.get(name));
		}

		return null;
	}
}