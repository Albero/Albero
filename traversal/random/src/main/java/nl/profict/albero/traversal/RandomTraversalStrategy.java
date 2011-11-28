package nl.profict.albero.traversal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import nl.profict.albero.configuration.Configuration;
import nl.profict.albero.configuration.ConfigurationException;
import nl.profict.albero.model.Context;
import nl.profict.albero.model.Node;
import nl.profict.albero.model.Tree;
import nl.profict.albero.utilities.Logger;

/**
 * A traversal strategy that picks a random node, but prefers nodes in the same group.
 *
 * @author levi_h
 */
public class RandomTraversalStrategy implements TraversalStrategy {
	private Random random;

	private final Logger logger;

	/**
	 * Creates a random traversal strategy.
	 */
	public RandomTraversalStrategy() {
		logger = Logger.get(getClass());
	}

	public void initialise(Configuration configuration, Map<String, String> parameters) {
		random = new Random();
	}

	public void destroy() throws ConfigurationException {}

	public String getName() {
		return "random";
	}

	public String getNode(Tree tree, Context context) {
		List<String> evaluatableNodes = new ArrayList<String>();
		List<String> evaluatableNodesWithPreviousGroup = new ArrayList<String>();

		String previousGroup = context.getVariableNames(Context.ALBERO).contains("node group")
			? (String) context.getVariable(Context.ALBERO, "node group") : null;

		for (String code: tree.getNodeCodes()) {
			Node node = tree.findNode(code);

			if (node.isEvaluatable(context)) {
				evaluatableNodes.add(code);

				if ((previousGroup != null) && previousGroup.equals(node.getGroup())) {
					evaluatableNodesWithPreviousGroup.add(code);
				}
			}
		}

		List<String> nodes = evaluatableNodesWithPreviousGroup.isEmpty()
			? evaluatableNodes : evaluatableNodesWithPreviousGroup;

		logger.debug("picking random node from ", nodes);

		return nodes.isEmpty() ? null : nodes.get(random.nextInt(nodes.size()));
	}
}