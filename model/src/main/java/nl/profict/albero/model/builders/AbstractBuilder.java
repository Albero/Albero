package nl.profict.albero.model.builders;

import nl.profict.albero.utilities.Logger;

/**
 * Simplifies implementing the {@link Builder builder interface}.
 *
 * @author levi_h
 * @param <B> the type of the object that is being built by the builder
 * @param <I> the type of the buildable object
 */
abstract class AbstractBuilder<B, I extends Buildable> implements Builder<B> {
	/** The object that is being built. */
	protected final I buildableObject;

	/** The logger to use. */
	protected final Logger logger;

	/**
	 * Creates an abstract builder.
	 *
	 * @param buildableObject the buildable object to use
	 */
	protected AbstractBuilder(I buildableObject) {
		this.buildableObject = buildableObject;

		logger = Logger.get(getClass());
	}

	public B build() throws BuilderException {
		buildableObject.checkValues();

		try {
			// we have to cast here because we're not allowed to say AbstractBuilder<B, I extends Buildable & B>

			@SuppressWarnings("unchecked")
			B builtObject = (B) buildableObject;

			return builtObject;
		} catch (ClassCastException exception) {
			throw new BuilderException(exception, "can't cast buildable object");
		}
	}
}