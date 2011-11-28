package nl.profict.albero.model;

import nl.profict.albero.model.external.ExternalValueProvider;
import nl.profict.albero.model.text.Dictionary;

/**
 * The context in which {@link Node nodes} and {@link ResultProvider result providers} are evaluated.
 *
 * @author wanja krah
 */
public interface EvaluationContext {
	/**
	 * Returns the state of the evaluated tree.
	 *
	 * @return the current context
	 */
	Context getContext();

	/**
	 * Returns the dictionary of the evaluated tree.
	 *
	 * @return the dictionary to use
	 */
	Dictionary getDictionary();

	/**
	 * Finds an external value provider by its name.
	 *
	 * @param name the name of the required external value provider
	 * @return the external value provider with the given name
	 * @throws EvaluationException when no external value provider can be found with the given name
	 */
	ExternalValueProvider findExternalValueProvider(String name) throws EvaluationException;
}