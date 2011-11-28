package nl.profict.albero.parsers.groovy

import nl.profict.albero.configuration.Configuration
import nl.profict.albero.model.CompositeTree
import nl.profict.albero.model.Tree
import nl.profict.albero.parsers.Parser
import nl.profict.albero.parsers.groovy.builders.TreeContainer

/**
 * Parses <a href="http://groovy.codehaus.org">Groovy</a> scripts.
 *
 * @author levi_h
 */
class GroovyParser implements Parser {
	private Configuration configuration

	private GroovyClassLoader loader

	private Map cache = [:]

	/**
	 * Creates a Groovy parser.
	 */
	public GroovyParser() {}

	String getName() {
		"groovy"
	}

	void initialise(Configuration configuration, Map<String, String> parameters) {
		this.configuration = configuration

		loader = new GroovyClassLoader()
	}

	Tree parse(String tree) {
		cache.containsKey(tree) ? cache[tree] : parseAndCache(tree)
	}

	private Tree parseAndCache(String tree) {
		def treeContainer = new TreeContainer(configuration)

		def treeConfigurer = loader.parseClass(tree).newInstance()
		treeConfigurer.metaClass.methodMissing = {String name, args ->
			treeContainer."$name"(*args)
		}
		treeConfigurer.run()

		Tree parsedTree = treeContainer.build()

		if (!(parsedTree instanceof CompositeTree)) {
			cache[tree] = parsedTree
		}

		parsedTree
	}

	void destroy() {}
}