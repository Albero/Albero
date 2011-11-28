package nl.profict.albero.model.external;

import java.util.Map;

/**
 * Provides a value from a source outside the model.
 * <p>
 * It is possible that the provision depends on some parameters; in that case, implementations are required to document
 * that.
 *
 * @author wanja krah
 */
public interface ExternalValueProvider {
	/**
	 * Returns the name of this external value provider. It should be unique.
	 *
	 * @return this external value provider's name
	 */
	String getName();

	/**
	 * Retrieves an external value, based on a number of parameters.
	 *
	 * @param parameters parameters for the provision
	 * @return the external value
	 */
	Object provideValue(Map<String, ?> parameters);
}