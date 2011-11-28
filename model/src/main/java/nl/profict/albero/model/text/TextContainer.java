package nl.profict.albero.model.text;

import java.util.Map;

/**
 * Contains a number of texts.
 *
 * @author levi_h
 */
public interface TextContainer {
	/**
	 * Returns all of the texts that this container contains, keyed by type.
	 *
	 * @return all of this text container's texts
	 */
	Map<String, String> getTexts();
}