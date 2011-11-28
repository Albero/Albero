package nl.profict.albero.parsers;

import nl.profict.albero.configuration.ConfigurationElement;
import nl.profict.albero.model.Tree;

/**
 * Parses the string representation of a {@link Tree}.
 *
 * @author levi_h
 */
public interface Parser extends ConfigurationElement {
	/**
	 * Returns the name of this parser.
	 *
	 * @return this parser's unique name
	 */
	String getName();

	/**
	 * Parses a tree.
	 *
	 * @param tree the tree to parse
	 * @return the parsed tree
	 * @throws ParseException when the tree can't be parsed
	 */
	Tree parse(String tree) throws ParseException;
}