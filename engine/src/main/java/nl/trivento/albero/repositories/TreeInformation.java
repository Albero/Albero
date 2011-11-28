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
package nl.trivento.albero.repositories;

/**
 * Information about a tree that is stored in a repository.
 *
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
