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

import nl.trivento.albero.model.forms.Form;
import nl.trivento.albero.model.forms.FormElement;

/**
 * Builds a {@link Form form}.
 *
 */
public class FormBuilder extends AbstractBuilder<Form, FormBuilder.BuildableForm> {
	/**
	 * Creates a form builder.
	 */
	public FormBuilder() {
		super(new BuildableForm());
	}

	/**
	 * Sets the name of the form.
	 *
	 * @param name the form name
	 * @return this form builder
	 */
	public FormBuilder setName(String name) {
		buildableObject.setValue("name", String.class, name);

		return this;
	}

	/**
	 * Adds a text.
	 *
	 * @param type the type of the text to add
	 * @param text the text to add
	 * @return this form builder
	 */
	public FormBuilder addText(String type, String text) {
		buildableObject.addValue("texts", String.class, String.class, type, text);

		return this;
	}

	/**
	 * Adds a form.
	 *
	 * @param form the form to add
	 * @return this form builder
	 */
	public FormBuilder addForm(Form form) {
		buildableObject.addValue("forms", Form.class, form);

		return this;
	}

	/**
	 * Adds a form element.
	 *
	 * @param element the form element to add
	 * @return this form builder
	 */
	public FormBuilder addElement(FormElement element) {
		buildableObject.addValue("elements", FormElement.class, element);

		return this;
	}

	/**
	 * A buildable form.
	 *
	 */
	static class BuildableForm extends AbstractBuildable implements Form {
		/**
		 * Creates a buildable form.
		 */
		public BuildableForm() {
			addProperty("name", String.class);
			addMapProperty("texts", String.class, String.class);
			addListProperty("forms", Form.class);
			addListProperty("elements", FormElement.class);
		}

		@Override
		public void checkValues() throws BuilderException {
			super.checkValues();

			if (!getForms().isEmpty() && !getElements().isEmpty()) {
				throw new BuilderException("a form cannot contain both subforms and elements");
			}
		}

		public String getName() {
			return getValue("name", String.class);
		}

		public Map<String, String> getTexts() {
			return getMap("texts", String.class, String.class);
		}

		public List<Form> getForms() {
			return getList("forms", Form.class);
		}

		public List<FormElement> getElements() {
			return getList("elements", FormElement.class);
		}

		@Override
		public String toString() {
			return String.format("buildable form (name: '%s', texts: %s, forms: %s, elements: %s)",
				getName(), getTexts(), getForms(), getElements());
		}
	}
}
