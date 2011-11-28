package nl.profict.albero.model.builders;

/**
 * Builds an object.
 *
 * @author levi_h
 * @param <B> the type of the built object
 */
public interface Builder<B> {
	/**
	 * Builds the object.
	 *
	 * @return the built object
	 * @throws BuilderException when the object can't be built (e.g. when it is incomplete)
	 */
	B build() throws BuilderException;
}