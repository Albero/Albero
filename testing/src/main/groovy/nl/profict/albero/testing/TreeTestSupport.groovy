package nl.profict.albero.testing

import nl.profict.albero.Engine
import nl.profict.albero.configuration.DefaultConfiguration
import nl.profict.albero.testing.builders.trees.TreeTestContainerContainer

import org.testng.annotations.BeforeClass

/**
 * Base class for tests that check tree definitions.
 *
 * @author levi_h
 */
abstract class TreeTestSupport {
	protected Engine engine

	private GroovyClassLoader loader

	@BeforeClass
	void createEngine() {
		engine = new Engine(new DefaultConfiguration([
			(DefaultConfiguration.REPOSITORY_CLASS): 'nl.profict.albero.repositories.ClassPathRepository',
			(DefaultConfiguration.PARSER_CLASSES): 'nl.profict.albero.parsers.groovy.GroovyParser',
			(DefaultConfiguration.TRAVERSAL_STRATEGY_CLASSES): 'nl.profict.albero.traversal.RandomTraversalStrategy'
		]))

		loader = new GroovyClassLoader()
	}

	/**
	 * Runs a test script and checks whether all expectations in the script are met.
	 *
	 * @param scriptName the name of the script to run
	 */
	protected void checkScript(String scriptName) {
		def container = new TreeTestContainerContainer()

		def script = loader.parseClass(loader.getResource(scriptName).text).newInstance()
		script.metaClass.methodMissing = {String name, args ->
			container."$name"(*args)
		}
		script.run()

		def messages = []

		container.build().each {test ->
			messages += test.checkExpectations(engine.evaluate(test.context))
		}

		assert messages.empty: messages.join(', ')
	}
}