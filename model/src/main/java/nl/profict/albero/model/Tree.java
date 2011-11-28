package nl.profict.albero.model;

import java.util.List;

import nl.profict.albero.model.model.Model;
import nl.profict.albero.model.text.Dictionary;

/**
 * A decision tree.
 *
 * @author levi_h
 */
public interface Tree {
	/**
	 * Returns this tree's code. It is unique for an organisation.
	 *
	 * @return the code of this tree
	 */
	String getCode();

	/**
	 * Returns this tree's dictionary.
	 *
	 * @return the dictionary of this tree
	 */
	Dictionary getDictionary();

	/**
	 * Returns the model of this tree.
	 *
	 * @return this tree's model
	 */
	Model getModel();

	/**
	 * Returns the codes of all of the nodes in this tree.
	 *
	 * @return the codes of this tree's nodes
	 */
	List<String> getNodeCodes();

	/**
	 * Finds a node in this decision tree by its code.
	 *
	 * @param code the code of the node to return
	 * @return the node with the given code or {@code null} when no such node exists in this tree
	 */
	Node findNode(String code);

	/**
	 * Returns the result model of this tree.
	 *
	 * @return this tree's result model
	 */
	Model getResultModel();

	/**
	 * Returns all result providers of this tree.
	 *
	 * @return this tree's result providers
	 */
	List<ResultProvider> getResultProviders();
}