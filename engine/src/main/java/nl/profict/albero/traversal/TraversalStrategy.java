package nl.profict.albero.traversal;

import nl.profict.albero.configuration.ConfigurationElement;
import nl.profict.albero.model.Context;
import nl.profict.albero.model.Node;
import nl.profict.albero.model.Tree;

/**
 * Determines the order in which {@link Node nodes} are evaluated.
 *
 * @author levi_h
 */
public interface TraversalStrategy extends ConfigurationElement {
	/**
	 * Returns the name of this traversal strategy.
	 *
	 * @return this traversal strategy's name
	 */
	String getName();

	/**
	 * Determines, in a certain context, which node should be evaluated.
	 *
	 * @param tree the tree to inspect
	 * @param context the context
	 * @return the code of the node that should be evaluated; {@code null} if no node can be evaluated
	 */
	String getNode(Tree tree, Context context);
}