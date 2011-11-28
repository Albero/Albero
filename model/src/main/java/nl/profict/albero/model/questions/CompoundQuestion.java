package nl.profict.albero.model.questions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nl.profict.albero.model.Context;
import nl.profict.albero.model.builders.FormBuilder;
import nl.profict.albero.model.text.Dictionary;
import nl.profict.albero.model.validation.ValidationResults;

/**
 * A question that is made up of other questions.
 *
 * @author levi_h
 */
public class CompoundQuestion extends AbstractQuestion {
	private List<Question> questions;

	/**
	 * Creates a compound question.
	 */
	public CompoundQuestion() {
		questions = new ArrayList<Question>();
	}

	/**
	 * Adds a question to this compound question.
	 *
	 * @param question the question to add
	 */
	public void addQuestion(Question question) {
		questions.add(question);
	}

	public boolean isAnswered(Context context) {
		boolean answered = true;

		Iterator<Question> it = questions.iterator();

		while (answered && it.hasNext()) {
			answered = it.next().isAnswered(context);
		}

		return answered;
	}

	public void validate(Context context, Dictionary dictionary, ValidationResults validationResults) {
		for (Question question: questions) {
			question.validate(context, dictionary, validationResults);
		}
	}

	public void ask(Context context, boolean validate, Dictionary dictionary, FormBuilder formBuilder) {
		FormBuilder subformBuilder = new FormBuilder();

		for (Map.Entry<String, String> text: translateTexts(context, dictionary).entrySet()) {
			subformBuilder.addText(text.getKey(), text.getValue());
		}

		for (Question question: questions) {
			question.ask(context, validate, dictionary, subformBuilder);
		}

		formBuilder.addForm(subformBuilder.build());
	}

	@Override
	public String toString() {
		return String.format("compound question (texts: %s, questions: %s)", getTexts(), questions);
	}
}