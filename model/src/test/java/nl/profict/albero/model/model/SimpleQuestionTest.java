package nl.profict.albero.model.model;

import java.util.List;

import nl.profict.albero.model.Context;
import nl.profict.albero.model.DefaultContext;
import nl.profict.albero.model.builders.FormBuilder;
import nl.profict.albero.model.forms.FormElement;
import nl.profict.albero.model.questions.SimpleQuestion;
import nl.profict.albero.model.text.DefaultDictionary;
import nl.profict.albero.model.text.Dictionary;
import nl.profict.albero.model.validation.PropertyValidator;
import nl.profict.albero.model.validation.RequiredPropertyValidator;
import nl.profict.albero.model.validation.ValidationError;
import nl.profict.albero.model.validation.ValidationResults;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SuppressWarnings("all")
@Test
public class SimpleQuestionTest {
	private Property property;
	private SimpleQuestion question;

	private Dictionary dictionary;

	@BeforeMethod
	public void createPropertyQuestionAndDictionary() {
		property = new Property("name", new SimplePropertyType("text"));

		DefaultModel model = new DefaultModel();
		model.addProperty(property);

		question = new SimpleQuestion(model, "name");
		question.addText("label", "questions.name");

		DefaultDictionary dictionary = new DefaultDictionary();

		dictionary.addTranslation("en", "model.name", "Name");
		dictionary.addTranslation("en", "questions.name", "What's your name again?");
		dictionary.addTranslation("en", RequiredPropertyValidator.ERROR_KEY, "{property} is required");

		this.dictionary = dictionary;
	}

	public void textsShouldBeParameterisable() {
		Context context = new DefaultContext();
		context.setVariable(Context.ALBERO, "role", "en");
		context.setVariable(Context.INFORMATION, "probableName", "Roger");

		question.addText("toolTip", "(We thought it was {information.probableName}.)");

		FormBuilder formBuilder = new FormBuilder();

		question.ask(context, false, dictionary, formBuilder);

		List<FormElement> elements = formBuilder.build().getElements();
		assert elements.size() == 1;
		assert elements.get(0).getTexts().get("toolTip").equals("(We thought it was Roger.)"): elements.get(0).getTexts().get("toolTip");
	}

	public void newQuestionShouldNotHaveAnyValidators() {
		Context context = new DefaultContext();
		context.setVariable(Context.ALBERO, "role", "en");
		context.setVariable(Context.INFORMATION, "name", "");

		ValidationResults results = new ValidationResults();
		question.validate(context, dictionary, results);
		assert !results.hasErrors();
	}

	public void validatorAddedToPropertyShouldBeUsedWhenValidating() {
		property.addValidator(new PropertyValidator() {
			public void validate(Context context, Model model, String propertyPath,
					Dictionary dictionary, ValidationResults validationResults) {
				Object value = context.getVariable(Context.INFORMATION, propertyPath);

				if ((value instanceof String) && !Character.isUpperCase(((String) value).charAt(0))) {
					validationResults.addError(new ValidationError("Names should be in upper case"));
				}
			}
		});

		Context context = new DefaultContext();
		context.setVariable(Context.ALBERO, "role", "en");
		context.setVariable(Context.INFORMATION, "name", "pierre");

		ValidationResults results = new ValidationResults();
		question.validate(context, dictionary, results);

		List<ValidationError> errors = results.getErrors();
		assert errors != null;
		assert errors.size() == 1;

		ValidationError error = errors.get(0);
		assert error != null;

		String errorMessage = error.getMessage();
		assert errorMessage != null;
		assert errorMessage.equals("Names should be in upper case");
	}

	public void validatorAddedToQuestionShouldBeUsedWhenValidating() {
		question.addValidator(new RequiredPropertyValidator());

		Context context = new DefaultContext();
		context.setVariable(Context.ALBERO, "role", "en");
		context.setVariable(Context.INFORMATION, "name", null);

		ValidationResults results = new ValidationResults();
		question.validate(context, dictionary, results);

		List<ValidationError> errors = results.getErrors();
		assert errors != null;
		assert errors.size() == 1;

		ValidationError error = errors.get(0);
		assert error != null;

		String errorMessage = error.getMessage();
		assert errorMessage != null;
		assert errorMessage.equals("Name is required");
	}
}