package nl.profict.albero.model;

/**
 * Uses a context to get to a result.
 *
 * @author levi_h
 */
public interface ResultProvider {
	/**
	 * Evaluates a context and adds a result (if applicable) to it.
	 *
	 * @param evaluationContext the evaluation context that contains the context to evaluate
	 * @throws EvaluationException when the context can't be evaluated
	 */
	void evaluate(EvaluationContext evaluationContext) throws EvaluationException;
}