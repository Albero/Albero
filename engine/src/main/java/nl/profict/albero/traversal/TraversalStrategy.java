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

import nl.profict.albero.configuration.ConfigurationElement;
import nl.profict.albero.model.Context;
import nl.profict.albero.model.Node;
import nl.profict.albero.model.Tree;

/**
 * Determines the order in which {@link Node nodes} are evaluated.
 *
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
