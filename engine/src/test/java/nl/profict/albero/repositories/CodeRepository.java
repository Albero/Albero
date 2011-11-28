package nl.profict.albero.repositories;

import java.util.Map;

import nl.profict.albero.configuration.Configuration;
import nl.profict.albero.parsers.CodeParser;

/**
 * A repository that delegates to the {@link CodeParser code parser}.
 *
 * @author levi_h
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