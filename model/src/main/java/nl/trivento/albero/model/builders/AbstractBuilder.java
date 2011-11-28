/* Copyright 2011-2012 Profict Holding 
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package nl.trivento.albero.model.builders;

import nl.trivento.albero.utilities.Logger;

/**
 * Simplifies implementing the {@link Builder builder interface}.
 *
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
