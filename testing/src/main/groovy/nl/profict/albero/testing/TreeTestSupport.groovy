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
package nl.profict.albero.testing

import nl.profict.albero.Engine
import nl.profict.albero.configuration.DefaultConfiguration
import nl.profict.albero.testing.builders.trees.TreeTestContainerContainer

import org.testng.annotations.BeforeClass

/**
 * Base class for tests that check tree definitions.
 *
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
