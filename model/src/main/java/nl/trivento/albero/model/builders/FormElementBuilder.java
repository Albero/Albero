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

import java.util.List;
import java.util.Map;

import nl.trivento.albero.model.forms.FormElement;
import nl.trivento.albero.model.forms.FormElementType;

/**
 * Builds a {@link FormElement form element}.
 *
 */
public class FormElementBuilder extends AbstractBuilder<FormElement, FormElementBuilder.BuildableFormElement> {
	/**
	 * Creates a form element builder.
	 */
	public FormElementBuilder() {
		super(new BuildableFormElement());
	}

	/**
	 * Sets the name of the form element.
	 *
	 * @param name the form element's name
	 * @return this form element builder
	 */
	public FormElementBuilder setName(String name) {
		buildableObject.setValue("name", String.class, name);

		return this;
	}

	/**
	 * Sets the type of the form element.
	 *
	 * @param type the form element's type
	 * @return this form element builder
	 */
	public FormElementBuilder setType(FormElementType type) {
		buildableObject.setValue("type", FormElementType.class, type);

		return this;
	}

	/**
	 * Sets the rendering hint of the form element.
	 *
	 * @param renderingHint the form element's rendering hint
	 * @return this form element builder
	 */
	public FormElementBuilder setRenderingHint(String renderingHint) {
		buildableObject.setValue("renderingHint", String.class, renderingHint);

		return this;
	}

	/**
	 * Adds a validation error.
	 *
	 * @param errorMessage the validation error to add
	 * @return this form element builder
	 */
	public FormElementBuilder addValidationError(String errorMessage) {
		buildableObject.addValue("validationErrors", String.class, errorMessage);

		return this;
	}

	/**
	 * Adds an option.
	 *
	 * @param option the option to add
	 * @return this form element builder
	 */
	public FormElementBuilder addOption(Object option) {
		buildableObject.addValue("options", Object.class, option);

		return this;
	}

	/**
	 * Adds a text.
	 *
	 * @param type the type of the text to add
	 * @param text the text to add
	 * @return this form element builder
	 */
	public FormElementBuilder addText(String type, String text) {
		buildableObject.addValue("texts", String.class, String.class, type, text);

		return this;
	}

	/**
	 * A buildable form element.
	 *
	 */
	static class BuildableFormElement extends AbstractBuildable implements FormElement {
		/**
		 * Creates a buildable form element.
		 */
		public BuildableFormElement() {
			addRequiredProperty("name", String.class);
			addRequiredProperty("type", FormElementType.class);
			addProperty("renderingHint", String.class);
			addListProperty("validationErrors", String.class);
			addListProperty("options", Object.class);
			addMapProperty("texts", String.class, String.class);
		}

		public String getName() {
			return getValue("name", String.class);
		}

		public FormElementType getType() {
			return getValue("type", FormElementType.class);
		}

		public String getRenderingHint() {
			return getValue("renderingHint", String.class);
		}

		public List<String> getValidationErrors() {
			return getList("validationErrors", String.class);
		}

		public List<?> getOptions() {
			return getList("options", Object.class);
		}

		public Map<String, String> getTexts() {
			return getMap("texts", String.class, String.class);
		}

		@Override
		public String toString() {
			return String.format("buildable form element (name: '%s', type: %s, validation errors: %s, texts: %s)",
				getName(), getType(), getValidationErrors(), getTexts());
		}
	}
}
