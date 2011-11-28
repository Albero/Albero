package nl.profict.albero.model;

import java.util.Map;

import nl.profict.albero.model.forms.Form;

/**
 * A node in a {@link Tree decision tree}.
 *
 * @author levi_h
 */
public interface Node {
	/**
	 * Returns the code of this node. It is unique within a tree.
	 *
	 * @return this node's code
	 */
	String getCode();

	/**
	 * Returns the group of this node. Node groups are optional.
	 *
	 * @return this node's group (may be {@code null})
	 */
	String getGroup();

	/**
	 * Returns all of the dynamic properties of this node.
	 *
	 * @return this node's dynamic properties
	 */
	Map<String, ?> getDynamicProperties();

	/**
	 * Determines whether this node can be evaluated in a certain context.
	 *
	 * @param context the context to inspect
	 * @return {@code true} if this node's {@link #evaluate(EvaluationContext, boolean) evaluate method} may be used,
	 *         {@code false} otherwise
	 */
	boolean isEvaluatable(Context context);

	/**
	 * Evaluates this node in a certain context.
	 *
	 * @param evaluationContext the context of the evaluation
	 * @param validate whether or not to apply validation
	 * @return the form that should be presented to the user (may be {@code null})
	 * @throws EvaluationException when this node can't be evaluated
	 */
	Form evaluate(EvaluationContext evaluationContext, boolean validate) throws EvaluationException;
}