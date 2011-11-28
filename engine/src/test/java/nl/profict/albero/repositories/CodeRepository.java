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
package nl.profict.albero.repositories;

import java.util.Map;

import nl.profict.albero.configuration.Configuration;
import nl.profict.albero.parsers.CodeParser;

/**
 * A repository that delegates to the {@link CodeParser code parser}.
 *
 */
public class CodeRepository implements Repository {
	/**
	 * Creates a code repository.
	 */
	public CodeRepository() {}

	public void initialise(Configuration configuration, Map<String, String> parameters) {}

	public void destroy() {}

	public TreeInformation locate(String code) throws RepositoryException {
		return new TreeInformation(code, CodeParser.NAME);
	}
}
