package nl.profict.albero.repositories;

import java.util.Collections;

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