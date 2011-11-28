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

import java.util.Collections;

import nl.trivento.albero.repositories.ClassPathRepository;
import nl.trivento.albero.repositories.Repository;
import nl.trivento.albero.repositories.RepositoryException;
import nl.trivento.albero.repositories.TreeInformation;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SuppressWarnings("all")
@Test
public class ClassPathRepositoryTest {
	private Repository repository;

	@BeforeMethod
	public void createRepository() {
		repository = new ClassPathRepository();

		repository.initialise(null, Collections.<String, String>emptyMap()); // TODO
	}

	// TODO treesInJarShouldBeLocatable

	public void treesOnFileSystemShouldBeLocatable() {
		TreeInformation information = repository.locate("hello");
		assert information != null;

		String tree = information.getTree();
		assert tree != null;
		assert tree.equals(".");

		String parser = information.getParser();
		assert parser != null;
		assert parser.equals("tree");
	}

	@Test(expectedExceptions = RepositoryException.class)
	public void locatingTreeWithUnknownCodeShouldCauseException() {
		repository.locate("nonexistent");
	}
}
