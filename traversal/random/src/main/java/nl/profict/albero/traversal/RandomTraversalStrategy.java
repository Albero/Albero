/* Copyright 2011-2012 Profict Holding 
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
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
