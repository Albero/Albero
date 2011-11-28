package nl.profict.albero.model;

/**
 * A condition that a {@link Context context} has to conform to before a {@link Node node} can be evaluated.
 *
 * @author levi_h
 */
public interface NodeCondition {
	/**
	 * Determines whether this condition applies or not.
	 *
	 * @param context the context in which this condition should be evaluated
	 * @return {@code true} if this condition applies, {@code false} otherwise
	 */
	boolean applies(Context context);
}