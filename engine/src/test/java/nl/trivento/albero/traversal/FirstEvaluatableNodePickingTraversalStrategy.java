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
package nl.trivento.albero.traversal;

import java.util.Iterator;
import java.util.Map;

import nl.trivento.albero.configuration.Configuration;
import nl.trivento.albero.model.Context;
import nl.trivento.albero.model.Node;
import nl.trivento.albero.model.Tree;
import nl.trivento.albero.traversal.TraversalStrategy;

/**
 * A traversal strategy that picks the first evaluatable node.
 *
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
