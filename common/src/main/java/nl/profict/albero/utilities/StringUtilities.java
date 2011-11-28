package nl.profict.albero.utilities;

import java.util.Map;

/**
 * Contains utility methods related to strings.
 *
 * @author levi_h
 */
public class StringUtilities {
	private StringUtilities() {}

	/**
	 * Joins the string representation of a number of objects.
	 *
	 * @param parts the parts to join
	 * @return a concatenation of the textual rendition of the parts
	 */
	public static String join(Object... parts) {
		StringBuilder builder = new StringBuilder();

		for (Object part: parts) {
			builder.append(part);
		}

		return builder.toString();
	}

	/**
	 * Replaces parameters (within braces) in a text.
	 *
	 * @param text the text to interpolate
	 * @param parameters the parameters to use
	 * @return the given text, interpolated with the given parameters
	 */
	public static String interpolate(String text, Map<String, ?> parameters) {
		String interpolatedText = text;

		do {
			text = interpolatedText;

			for (String parameterName: parameters.keySet()) {
				String target = String.format("{%s}", parameterName);
				String replacement = String.valueOf(parameters.get(parameterName));

				interpolatedText = interpolatedText.replace(target, replacement);
			}
		} while (!interpolatedText.equals(text));

		return interpolatedText;
	}
}