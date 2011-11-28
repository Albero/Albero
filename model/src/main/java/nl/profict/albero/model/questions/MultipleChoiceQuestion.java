package nl.profict.albero.model.questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.profict.albero.model.Context;
import nl.profict.albero.model.builders.FormElementBuilder;
import nl.profict.albero.model.model.Model;
import nl.profict.albero.model.text.Dictionary;

/**
 * A question with one or more answers that are selected from a certain set of options.
 *
 * @author levi_h
 */
public class MultipleChoiceQuestion extends SimpleQuestion {
	private List<?> options;

	/**
	 * Creates a multiple choice question.
	 *
	 * @param options the possible answers
	 * @param model the model that contains the property
	 * @param propertyPath the path to the property
	 */
	public MultipleChoiceQuestion(List<?> options, Model model, String propertyPath) {
		super(model, propertyPath);

		this.options = convertOptions(options, model.getProperty(propertyPath).getType().getName());
	}

	private List<?> convertOptions(List<?> options, String propertyType) {
		List<Object> convertedOptions = new ArrayList<Object>();

		for (Object option: options) {
			Object convertedOption;

			if (propertyType.equals("number") && (option instanceof Number)) {
				convertedOption = ((Number) option).longValue();
			} else {
				convertedOption = option;
			}

			convertedOptions.add(convertedOption);
		}

		return convertedOptions;
	}

	@Override
	public boolean isAnswered(Context context) {
		return super.isAnswered(context) && isValidOption(context);
	}

	private boolean isValidOption(Context context) {
		Object answer = context.getVariable(Context.INFORMATION, getPropertyPath());

		return options.containsAll(getProperty().isList() ? (List<?>) answer : Collections.singletonList(answer));
	}

	@Override
	protected void fillElementBuilder(FormElementBuilder elementBuilder, Context context, Dictionary dictionary) {
		super.fillElementBuilder(elementBuilder, context, dictionary);

		for (Object option: options) {
			elementBuilder.addOption(option);
		}
	}
}