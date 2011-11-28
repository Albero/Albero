package nl.profict.albero.traversal;

import java.util.Iterator;
import java.util.Map;

import nl.profict.albero.configuration.Configuration;
import nl.profict.albero.model.Context;
import nl.profict.albero.model.Node;
import nl.profict.albero.model.Tree;

/**
 * A traversal strategy that picks the first evaluatable node.
 *
 * @author levi_h
 */
public class FirstEvaluatableNodePickingTraversalStrategy implements TraversalStrategy {
	/**
	 * Creates a first evaluatable node picking traversal strategy.
	 */
	public FirstEvaluatableNodePickingTraversalStrategy() {}

	public void initialise(Configuration configuration, Map<String, String> parameters) {}

	public void destroy() {}

	public String getName() {
		return "first evaluatable node picker";
	}

	public String getNode(Tree tree, Context context) {
		Iterator<String> codes = tree.getNodeCodes().iterator();
		String code = null;

		while (codes.hasNext() && (code == null)) {
			Node node = tree.findNode(codes.next());

			if (node.isEvaluatable(context)) {
				code = node.getCode();
			}
		}

		return code;
	}

	@Override
	public String toString() {
		return "first evaluatable node picking traversal strategy";
	}
}