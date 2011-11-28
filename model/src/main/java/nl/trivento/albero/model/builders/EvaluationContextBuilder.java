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

import java.util.Map;

import nl.trivento.albero.model.Context;
import nl.trivento.albero.model.EvaluationContext;
import nl.trivento.albero.model.EvaluationException;
import nl.trivento.albero.model.external.ExternalValueProvider;
import nl.trivento.albero.model.text.Dictionary;

/**
 * Builds an {@link EvaluationContext evaluation context}.
 *
 */
public class EvaluationContextBuilder
		extends AbstractBuilder<EvaluationContext, EvaluationContextBuilder.BuildableEvaluationContext> {
	/**
	 * Creates an evaluation context builder.
	 */
	public EvaluationContextBuilder() {
		super(new BuildableEvaluationContext());
	}

	/**
	 * Sets the context.
	 *
	 * @param context the current context
	 * @return this evaluation context builder
	 */
	public EvaluationContextBuilder setContext(Context context) {
		buildableObject.setValue("context", Context.class, context);

		return this;
	}

	/**
	 * Sets the dictionary.
	 *
	 * @param dictionary the dictionary to use
	 * @return this evaluation context builder
	 */
	public EvaluationContextBuilder setDictionary(Dictionary dictionary) {
		buildableObject.setValue("dictionary", Dictionary.class, dictionary);

		return this;
	}

	/**
	 * Adds an external value provider.
	 *
	 * @param externalValueProvider the external value provider to add
	 * @return this evaluation context builder
	 */
	public EvaluationContextBuilder addExternalValueProvider(ExternalValueProvider externalValueProvider) {
		buildableObject.addValue("externalValueProviders", String.class, ExternalValueProvider.class,
				externalValueProvider.getName(), externalValueProvider);

		return this;
	}

	/**
	 * A buildable evaluation context.
	 *
	 */
	static class BuildableEvaluationContext extends AbstractBuildable implements EvaluationContext {
		/**
		 * Creates a buildable evaluation context.
		 */
		public BuildableEvaluationContext() {
			addProperty("context", Context.class);
			addProperty("dictionary", Dictionary.class);
			addMapProperty("externalValueProviders", String.class, ExternalValueProvider.class);
		}

		public Context getContext() {
			return getValue("context", Context.class);
		}

		public Dictionary getDictionary() {
			return getValue("dictionary", Dictionary.class);
		}

		public ExternalValueProvider findExternalValueProvider(String name)
				throws EvaluationException {
			Map<String, ExternalValueProvider> externalValueProviders =
				getMap("externalValueProviders", String.class, ExternalValueProvider.class);

			if (externalValueProviders.containsKey(name)) {
				return externalValueProviders.get(name);
			} else {
				throw new EvaluationException("unknown external value provider: '", name, "'");
			}
		}
	}
}
