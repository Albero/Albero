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
package nl.profict.albero.model.builders;

import nl.profict.albero.model.forms.FormElementType;

/**
 * Builds a {@link FormElementType form element type}.
 *
 * @author levi_h
 */
public class FormElementTypeBuilder extends AbstractBuilder<FormElementType, FormElementTypeBuilder.BuildableFormElementType> {
	/**
	 * Creates a form element type builder.
	 */
	public FormElementTypeBuilder() {
		super(new BuildableFormElementType());
	}

	/**
	 * Sets the name of the form element type.
	 *
	 * @param name the form element type's name
	 * @return this form element type builder
	 */
	public FormElementTypeBuilder setName(String name) {
		buildableObject.setValue("name", String.class, name);

		return this;
	}

	/**
	 * Sets whether the form element type allows lists.
	 *
	 * @param list whether or not multiple answers may be given to form elements with the form element type
	 * @return this form element type builder
	 */
	public FormElementTypeBuilder setList(boolean list) {
		buildableObject.setValue("list", Boolean.TYPE, list);

		return this;
	}

	/**
	 * A buildable form element type.
	 *
	 * @author levi_h
	 */
	static class BuildableFormElementType extends AbstractBuildable implements FormElementType {
		/**
		 * Creates a buildable form element type.
		 */
		public BuildableFormElementType() {
			addRequiredProperty("name", String.class);
			addProperty("list", Boolean.TYPE, false);
		}

		public String getName() {
			return getValue("name", String.class);
		}

		public boolean isList() {
			return getValue("list", Boolean.TYPE);
		}

		@Override
		public String toString() {
			return String.format("buildable form element type (name: '%s', list: %b)", getName(), isList());
		}
	}
}
