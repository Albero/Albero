package nl.profict.albero.model.builders;

/**
 * An object that can be built using a {@link Builder builder}.
 *
 * @author levi_h
 */
public interface Buildable {
	/**
	 * Checks whether this object can be built using the given values.
	 *
	 * @throws BuilderException when this buildable object's values are incomplete or illegal
	 */
	void checkValues() throws BuilderException;
}