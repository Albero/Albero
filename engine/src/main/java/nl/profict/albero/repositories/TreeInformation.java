package nl.profict.albero.repositories;

/**
 * Information about a tree that is stored in a repository.
 *
 * @author levi_h
 */
public class TreeInformation {
	private String tree;
	private String parser;

	/**
	 * Creates tree information.
	 *
	 * @param tree a tree that can be parsed
	 * @param parser the parser that should be used to parse the tree
	 */
	public TreeInformation(String tree, String parser) {
		this.tree = tree;
		this.parser = parser;
	}

	/**
	 * Returns the tree that can be parsed.
	 *
	 * @return the tree
	 */
	public String getTree() {
		return tree;
	}

	/**
	 * Returns the parser that should be used for the tree.
	 *
	 * @return the parser to use
	 */
	public String getParser() {
		return parser;
	}

	@Override
	public String toString() {
		return String.format("tree information (tree: %s, parser: %s)", tree, parser);
	}
}