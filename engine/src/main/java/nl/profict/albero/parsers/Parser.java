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
