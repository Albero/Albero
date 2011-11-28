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
package nl.profict.albero.model.questions;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nl.profict.albero.model.Context;
import nl.profict.albero.model.builders.FormBuilder;
import nl.profict.albero.model.builders.FormElementBuilder;
import nl.profict.albero.model.builders.FormElementTypeBuilder;
import nl.profict.albero.model.model.Model;
import nl.profict.albero.model.model.Property;
import nl.profict.albero.model.model.SimplePropertyType;
import nl.profict.albero.model.text.Dictionary;
import nl.profict.albero.model.validation.PropertyValidator;
import nl.profict.albero.model.validation.ValidationError;
import nl.profict.albero.model.validation.ValidationResults;

/**
 * A question that answers a property of a {@link SimplePropertyType simple type}.
 *
 */
public class SimpleQuestion extends AbstractQuestion {
	private Model model;
	private String propertyPath;

	private Set<PropertyValidator> validators;

	private String renderingHint;

	/**
	 * Creates a simple question.
	 *
	 * @param model the model that contains the property
	 * @param propertyPath the path to the property
	 */
	public SimpleQuestion(Model model, String propertyPath) {
		this.model = model;
		this.propertyPath = propertyPath;

		validators = new HashSet<PropertyValidator>();
	}

	/**
	 * Returns the model that contains the answered property.
	 *
	 * @return the model used
	 */
	protected Model getModel() {
		return model;
	}

	/**
	 * Returns the path to the answered property.
	 *
	 * @return the property path used
	 */
	protected String getPropertyPath() {
		return propertyPath;
	}

	/**
	 * Returns the answered property.
	 *
	 * @return the answered property
	 */
	protected Property getProperty() {
		return getModel().getProperty(getPropertyPath());
	}

	public boolean isAnswered(Context context) {
		return context.getVariableNames(Context.INFORMATION).contains(propertyPath);
	}

	/**
	 * Adds a property validator. It will be used when {@link #validate(Context, Dictionary, ValidationResults)
	 * validating} this question.
	 *
	 * @param validator the validator to add
	 */
	public void addValidator(PropertyValidator validator) {
		validators.add(validator);
	}

	public void validate(Context context, Dictionary dictionary, ValidationResults validationResults) {
		for (PropertyValidator validator: getProperty().getValidators()) {
			validator.validate(context, model, propertyPath, dictionary, validationResults);
		}

		for (PropertyValidator validator: validators) {
			validator.validate(context, model, propertyPath, dictionary, validationResults);
		}
	}

	/**
	 * Returns an optional hint to the renderer of this question.
	 *
	 * @return this question's rendering hint (may be {@code null})
	 */
	public String getRenderingHint() {
		return renderingHint;
	}

	/**
	 * Sets a rendering hint. The renderer of this question may use it, though it is not required to.
	 *
	 * @param renderingHint the rendering hint to use
	 */
	public void setRenderingHint(String renderingHint) {
		this.renderingHint = renderingHint;
	}

	public void ask(Context context, boolean validate, Dictionary dictionary, FormBuilder formBuilder) {
		FormElementBuilder elementBuilder = new FormElementBuilder();
		fillElementBuilder(elementBuilder, context, dictionary);

		if (validate) {
			ValidationResults validationResults = new ValidationResults();
			validate(context, dictionary, validationResults);

			for (ValidationError validationError: validationResults.getErrors()) {
				elementBuilder.addValidationError(validationError.getMessage());
			}
		}

		formBuilder.addElement(elementBuilder.build());
	}

	/**
	 * Fills a form element builder. This method will be invoked from {@link
	 * #ask(Context, boolean, Dictionary, FormBuilder) the ask method}.
	 *
	 * @param elementBuilder the form element builder to fill
	 * @param context the context in which this question was asked
	 * @param dictionary the dictionary to use
	 */
	protected void fillElementBuilder(FormElementBuilder elementBuilder, Context context, Dictionary dictionary) {
		elementBuilder.setName(propertyPath);

		Property property = getProperty();

		FormElementTypeBuilder elementTypeBuilder = new FormElementTypeBuilder();
		elementTypeBuilder.setName(property.getType().getName());
		elementTypeBuilder.setList(property.isList());
		elementBuilder.setType(elementTypeBuilder.build());

		if (renderingHint != null) {
			elementBuilder.setRenderingHint(renderingHint);
		}

		for (Map.Entry<String, String> text: translateTexts(context, dictionary).entrySet()) {
			elementBuilder.addText(text.getKey(), text.getValue());
		}
	}

	@Override
	public String toString() {
		return String.format("simple question (texts: '%s', property: %s)", getTexts(), getProperty());
	}
}
