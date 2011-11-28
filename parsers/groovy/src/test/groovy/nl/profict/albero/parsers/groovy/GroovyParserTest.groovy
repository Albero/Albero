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
package nl.profict.albero.parsers.groovy

import nl.profict.albero.configuration.Configuration
import nl.profict.albero.model.Context
import nl.profict.albero.model.DefaultContext
import nl.profict.albero.model.EvaluationContext
import nl.profict.albero.model.Tree
import nl.profict.albero.model.builders.EvaluationContextBuilder
import nl.profict.albero.model.external.ExternalValueProvider
import nl.profict.albero.model.model.Model
import nl.profict.albero.model.model.Property
import nl.profict.albero.model.questions.QuestionNode
import nl.profict.albero.model.text.Dictionary
import nl.profict.albero.parsers.Parser
import nl.profict.albero.repositories.Repository
import nl.profict.albero.repositories.TreeInformation

import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class GroovyParserTest {
	private Repository repository
	private Parser parser

	private Context context

	@BeforeMethod
	void createParserAndContext() {
		repository = [
			locate: {code ->
				def classLoader = Thread.currentThread().contextClassLoader

				new TreeInformation(new File(classLoader.getResource("trees/${code}.albero").file).text, 'groovy')
			}
		] as Repository

		parser = new GroovyParser()
		parser.initialise([
			getRepository: {-> repository},
			getParsers: {-> [groovy: parser]}
		] as Configuration, [:])

		context = new DefaultContext()
		context.setVariable(Context.ALBERO, 'role', 'en')
	}

	@Test
	void treeWithOnlyCodeShouldBeParseable() {
		assert parseTree('code')?.code == 'code'
	}

	@Test
	void treeWithDictionaryShouldBeParseable() {
		Tree tree = parseTree('dictionary')
		assert tree != null
		assert tree.code == 'dictionary'

		Dictionary dictionary = tree.getDictionary()
		assert dictionary != null
		assert dictionary.findTranslation('en', 'model.name') == 'Name'
	}

	@Test
	void treeeWithDictionaryWithRoleShouldBeParseable() {
		Tree tree = parseTree('dictionary_with_role')
		assert tree != null
		assert tree.code == 'dictionary with role'

		Dictionary dictionary = tree.getDictionary()
		assert dictionary != null
		assert dictionary.findTranslation('en', 'model.name') == 'Name'
		assert dictionary.findTranslation('en', 'model.age') == 'Age'
	}

	@Test
	void treeWithSimpleModelShouldBeParseable() {
		Tree tree = parseTree('simple_model')
		assert tree != null
		assert tree.code == 'simple model'

		Model model = tree.model
		assert model != null

		Property name = model.getProperty('name')
		assert name != null
		assert name.name == 'name'
		assert !name.list
		assert name.type != null
		assert name.type.name == 'text'
		assert name.type.subproperties?.empty

		Property age = model.getProperty('age')
		assert age != null
		assert age.name == 'age'
		assert !age.list
		assert age.type != null
		assert age.type.name == 'number'
		assert age.type.subproperties?.empty
	}

	@Test
	void treeWithCompoundModelShouldBeParseable() {
		Tree tree = parseTree('compound_model')
		assert tree != null
		assert tree.code == 'compound model'

		Model model = tree.model
		assert model != null

		Property person = model.getProperty('person')
		assert person != null
		assert person.name == 'person'
		assert !person.list
		assert person.type != null
		assert person.type.name == 'person'
		assert person.type.subproperties != null
		assert person.type.subproperties.size() == 1

		Property address = person.type.subproperties[0]
		assert address != null
		assert address.name == 'address'
		assert !address.list
		assert address.type != null
		assert address.type.name == 'address'
		assert address.type.subproperties != null
		assert address.type.subproperties.size() == 2

		Property street = address.type.subproperties[0]
		assert street != null
		assert !street.list
		assert street.name == 'street'
		assert street.type != null
		assert street.type.name == 'text'
		assert street.type.subproperties?.empty

		Property city = address.type.subproperties[1]
		assert city != null
		assert city.name == 'city'
		assert !city.list
		assert city.type != null
		assert city.type.name == 'text'
		assert city.type.subproperties?.empty
	}

	@Test
	void treeWithSimpleQuestionShouldBeParseable() {
		Tree tree = parseTree('simple_question')
		assert tree != null
		assert tree.code == 'simple question'

		List nodeCodes = tree.getNodeCodes()
		assert nodeCodes != null
		assert nodeCodes.size() == 1

		def questionNode = tree.findNode(nodeCodes[0])
		assert questionNode instanceof QuestionNode

		def form = questionNode.evaluate(createEvaluationContext(tree), false)
		assert form != null

		def formElements = form.getElements()
		assert formElements != null
		assert formElements.size() == 1

		def formElement = formElements[0]
		assert formElement.name == 'name'
		assert formElement.type != null
		assert formElement.type.name == 'text'
		assert !formElement.type.list

		assert formElement.texts != null
		assert formElement.texts.size() == 1
		assert formElement.texts['label'] == 'What\'s your name?'
	}

	@Test
	void treeWithQuestionThatHasVariousTypesOfTextShouldBeParseable() {
		Tree tree = parseTree('question_with_various_text_types')
		assert tree != null
		assert tree.code == 'question with various text types'

		List nodeCodes = tree.getNodeCodes()
		assert nodeCodes != null
		assert nodeCodes.size() == 1

		def questionNode = tree.findNode(nodeCodes[0])
		assert questionNode instanceof QuestionNode

		def form = questionNode.evaluate(createEvaluationContext(tree), false)
		assert form != null

		def formElements = form.getElements()
		assert formElements != null
		assert formElements.size() == 1

		def formElement = formElements[0]
		assert formElement.name == 'car'
		assert formElement.type != null
		assert formElement.type.name == 'text'
		assert !formElement.type.list

		assert formElement.texts != null
		assert formElement.texts.size() == 2
		assert formElement.texts['introduction'] ==
			'We would now like to ask you some questions about your preferences.'
		assert formElement.texts['label'] == 'What would be your dream car?'
	}

	@Test
	void treeWithMultipleChoiceQuestionShouldBeParseable() {
		Tree tree = parseTree('multiple_choice_question')
		assert tree != null
		assert tree.code == 'multiple choice question'

		List nodeCodes = tree.getNodeCodes()
		assert nodeCodes != null
		assert nodeCodes.size() == 1

		def questionNode = tree.findNode(nodeCodes[0])
		assert questionNode instanceof QuestionNode

		def form = questionNode.evaluate(createEvaluationContext(tree), false)
		assert form != null

		def formElements = form.getElements()
		assert formElements != null
		assert formElements.size() == 1

		def formElement = formElements[0]
		assert formElement.name == 'favouriteColour'
		assert formElement.type != null
		assert formElement.type.name == 'text'
		assert !formElement.type.list
		assert formElement.options == ['red', 'yellow', 'blue']

		assert formElement.texts != null
		assert formElement.texts.size() == 1
		assert formElement.texts['label'] == 'What\'s your favourite colour?'
	}

	@Test
	void treeWithMultipleChoiceQuestionForListPropertyShouldBeParseable() {
		Tree tree = parseTree('multiple_choice_question_for_list_property')
		assert tree != null
		assert tree.code == 'multiple choice question for list property'

		Model model = tree.model
		assert model != null

		Property cities = model.getProperty('cities')
		assert cities != null
		assert cities.name == 'cities'
		assert cities.list
		assert cities.type != null
		assert cities.type.name == 'text'
		assert cities.type.subproperties != null
		assert cities.type.subproperties.empty

		List nodeCodes = tree.getNodeCodes()
		assert nodeCodes != null
		assert nodeCodes.size() == 1

		def questionNode = tree.findNode(nodeCodes[0])
		assert questionNode instanceof QuestionNode

		def form = questionNode.evaluate(createEvaluationContext(tree), false)
		assert form != null

		def formElements = form.getElements()
		assert formElements != null
		assert formElements.size() == 1

		def formElement = formElements[0]
		assert formElement.name == 'cities'
		assert formElement.type != null
		assert formElement.type.name == 'text'
		assert formElement.type.list
		assert formElement.options == ['Amsterdam', 'Utrecht', 'Groningen', 'Maastricht']

		assert formElement.texts != null
		assert formElement.texts.size() == 1
		assert formElement.texts['label'] == 'Which of these cities have you lived in?'
	}

	@Test
	void treeWithQuestionAndRenderingHintShouldBeParseable() {
		Tree tree = parseTree('multiple_choice_question_with_rendering_hint')
		assert tree != null
		assert tree.code == 'multiple choice question with rendering hint'

		List nodeCodes = tree.getNodeCodes()
		assert nodeCodes != null
		assert nodeCodes.size() == 1

		def questionNode = tree.findNode(nodeCodes[0])
		assert questionNode instanceof QuestionNode

		def form = questionNode.evaluate(createEvaluationContext(tree), false)
		assert form != null

		def formElements = form.getElements()
		assert formElements != null
		assert formElements.size() == 1

		def formElement = formElements[0]
		assert formElement != null
		assert formElement.getRenderingHint() == 'radio buttons'
	}

	@Test
	void treeWithCompoundQuestionShouldBeParseable() {
		Tree tree = parseTree('compound_question')
		assert tree != null
		assert tree.code == 'compound question'

		List nodeCodes = tree.getNodeCodes()
		assert nodeCodes != null
		assert nodeCodes.size() == 1

		def questionNode = tree.findNode(nodeCodes[0])
		assert questionNode instanceof QuestionNode

		def form = questionNode.evaluate(createEvaluationContext(tree), false)
		assert form != null

		def subforms = form.getForms()
		assert subforms != null
		assert subforms.size() == 1

		def subform = subforms[0]
		assert subform.texts != null
		assert subform.texts.size() == 1
		assert subform.texts['message'] == 'What is your name?'

		def formElements = subform.getElements()
		assert formElements != null
		assert formElements.size() == 2

		def firstFormElement = formElements[0]
		assert firstFormElement.name == 'firstName'
		assert firstFormElement.type != null
		assert firstFormElement.type.name == 'text'
		assert !firstFormElement.type.list

		assert firstFormElement.texts != null
		assert firstFormElement.texts.size() == 1
		assert firstFormElement.texts['label'] == 'First name'

		def secondFormElement = formElements[1]
		assert secondFormElement.name == 'lastName'
		assert secondFormElement.type != null
		assert secondFormElement.type.name == 'text'
		assert !secondFormElement.type.list

		assert secondFormElement.texts != null
		assert secondFormElement.texts.size() == 1
		assert secondFormElement.texts['label'] == 'Last name'
	}

	@Test
	void treeWithConditionalNodesShouldBeParseable() {
		Tree tree = parseTree('nodes_with_conditions')
		assert tree != null
		assert tree.code == 'nodes with conditions'

		List nodeCodes = tree.getNodeCodes()
		assert nodeCodes != null
		assert nodeCodes.size() == 1

		def node = tree.findNode(nodeCodes[0])
		assert node != null

		context.setVariable(Context.INFORMATION, 'male', false)
		assert !node.isEvaluatable(context)

		context.setVariable(Context.INFORMATION, 'male', true)
		assert node.isEvaluatable(context)
	}

	@Test
	void treeWithGroupedNodesShouldBeParseable() {
		Tree tree = parseTree('nodes_with_groups')
		assert tree != null
		assert tree.code == 'nodes with groups'

		List nodeCodes = tree.getNodeCodes()
		assert nodeCodes != null
		assert nodeCodes.size() == 2

		nodeCodes.each {nodeCode ->
			def node = tree.findNode(nodeCode)
			assert node != null
			assert node.group == 'holiday'
		}
	}

	@Test
	void treeWithPropertyValidationShouldBeParseable() {
		Tree tree = parseTree('property_validation')
		assert tree != null
		assert tree.code == 'property validation'

		List nodeCodes = tree.getNodeCodes()
		assert nodeCodes != null
		assert nodeCodes.size() == 1

		def node = tree.findNode(nodeCodes[0])
		assert node != null

		context.setVariable(Context.ALBERO, 'node', node.code)
		context.setVariable(Context.INFORMATION, 'age', null)

		def form = node.evaluate(createEvaluationContext(tree), true)
		assert form != null

		def formElements = form.getElements()
		assert formElements != null
		assert formElements.size() == 1

		def formElement = formElements[0]
		assert formElement.name == 'age'
		assert formElement.type != null
		assert formElement.type.name == 'number'
		assert !formElement.type.list

		assert formElement.texts != null
		assert formElement.texts.size() == 1
		assert formElement.texts['label'] == 'How old are you?'

		List validationErrors = formElement.validationErrors
		assert validationErrors != null
		assert validationErrors.size() == 1
		assert validationErrors[0] == 'Age is required.'
	}

	@Test
	void treeWithQuestionValidationShouldBeParseable() {
		Tree tree = parseTree('question_validation')
		assert tree != null
		assert tree.code == 'question validation'

		List nodeCodes = tree.getNodeCodes()
		assert nodeCodes != null
		assert nodeCodes.size() == 1

		def node = tree.findNode(nodeCodes[0])
		assert node != null

		context.setVariable(Context.ALBERO, 'node', node.code)
		context.setVariable(Context.INFORMATION, 'age', null)

		def form = node.evaluate(createEvaluationContext(tree), true)
		assert form != null

		def formElements = form.getElements()
		assert formElements != null
		assert formElements.size() == 1

		def formElement = formElements[0]
		assert formElement.name == 'age'
		assert formElement.type != null
		assert formElement.type.name == 'number'
		assert !formElement.type.list

		assert formElement.texts != null
		assert formElement.texts.size() == 1
		assert formElement.texts['label'] == 'How old are you?'

		List validationErrors = formElement.validationErrors
		assert validationErrors != null
		assert validationErrors.size() == 1
		assert validationErrors[0] == 'Age is required.'
	}

	@Test
	void treeWithExternalValueNodeShouldBeParseable() {
		Tree tree = parseTree('external_value')
		assert tree != null
		assert tree.code == 'external value'

		List nodeCodes = tree.getNodeCodes()
		assert nodeCodes != null
		assert nodeCodes.size() == 1

		def node = tree.findNode(nodeCodes[0])
		assert node != null

		context.setVariable(Context.ALBERO, 'node', node.code)

		node.evaluate(createEvaluationContext(tree, [
			getName: {-> 'time provider'},
			provideValue: {Map parameters -> '12:00'}
		] as ExternalValueProvider), false)

		assert context.getVariableNames(Context.INFORMATION) == ['currentTime'] as Set
		assert context.getVariable(Context.INFORMATION, 'currentTime') == '12:00'
	}

	@Test
	void treeWithParameterisedExternalValueNodeShouldBeParseable() {
		Tree tree = parseTree('external_value_with_parameter')
		assert tree != null
		assert tree.code == 'external value with parameter'

		List nodeCodes = tree.getNodeCodes()
		assert nodeCodes != null
		assert nodeCodes.size() == 1

		def node = tree.findNode(nodeCodes[0])
		assert node != null

		context.setVariable(Context.ALBERO, 'node', node.code)

		node.evaluate(createEvaluationContext(tree, [
			getName: {-> 'time provider'},
			provideValue: {Map parameters ->
				parameters['dst'] ? '13:00' : '12:00'
			}
		] as ExternalValueProvider), false)

		assert context.getVariableNames(Context.INFORMATION) == ['currentTime'] as Set
		assert context.getVariable(Context.INFORMATION, 'currentTime') == '13:00'
	}

	@Test
	void treeWithValuesNodeShouldBeParseable() {
		Tree tree = parseTree('values')
		assert tree != null
		assert tree.code == 'values'

		List nodeCodes = tree.getNodeCodes()
		assert nodeCodes != null
		assert nodeCodes.size() == 1

		def node = tree.findNode(nodeCodes[0])
		assert node != null

		context.setVariable(Context.ALBERO, 'node', node.code)

		node.evaluate(createEvaluationContext(tree), false)

		assert context.getVariableNames(Context.INFORMATION) == ['sports'] as Set
		assert context.getVariable(Context.INFORMATION, 'sports') == ['Soccer', 'Tennis', 'Hockey']
	}

	@Test
	void treeWithNestedValuesNodeShouldBeParseable() {
		Tree tree = parseTree('nested_values')
		assert tree != null
		assert tree.code == 'nested values'

		List nodeCodes = tree.getNodeCodes()
		assert nodeCodes != null
		assert nodeCodes.size() == 1

		def node = tree.findNode(nodeCodes[0])
		assert node != null

		context.setVariable(Context.ALBERO, 'node', node.code)

		node.evaluate(createEvaluationContext(tree), false)

		assert context.getVariableNames(Context.INFORMATION) == ['sports'] as Set
		assert context.getVariable(Context.INFORMATION, 'sports') == [
			[name: 'Soccer'],
			[name: 'Tennis'],
			[name: 'Hockey']
		]
	}

	@Test
	void treeWithCompoundResultModelShouldBeParseable() {
		Tree tree = parseTree('compound_result_model')
		assert tree != null
		assert tree.code == 'compound result model'

		Model resultModel = tree.resultModel
		assert resultModel != null

		Property salary = resultModel.getProperty('salary')
		assert salary != null
		assert salary.name == 'salary'
		assert !salary.list
		assert salary.type != null
		assert salary.type.name == 'salary'
		assert salary.type.subproperties != null
		assert salary.type.subproperties.size() == 1

		Property amount = salary.type.subproperties[0]
		assert amount != null
		assert amount.name == 'amount'
		assert !amount.list
		assert amount.type != null
		assert amount.type.name == 'number'
		assert amount.type.subproperties?.empty
	}

	@Test
	void treeWithUnconditionalRulesShouldBeParseable() {
		Tree tree = parseTree('rules_without_conditions')
		assert tree != null
		assert tree.code == 'rules without conditions'

		List resultProviders = tree.getResultProviders()
		assert resultProviders != null
		assert resultProviders.size() == 1

		def resultProvider = resultProviders[0]
		assert resultProvider != null
		resultProvider.evaluate(createEvaluationContext(tree))
		assert context.getVariableNames(Context.RESULTS).contains('climate')
		assert context.getVariable(Context.RESULTS, 'climate') == 'tropical'
	}

	@Test
	void treeWithConditionalRulesShouldBeParseable() {
		Tree tree = parseTree('rules_with_conditions')
		assert tree != null
		assert tree.code == 'rules with conditions'

		List resultProviders = tree.getResultProviders()
		assert resultProviders != null
		assert resultProviders.size() == 2

		context.setVariable(Context.INFORMATION, 'age', 16)

		resultProviders.each {resultProvider ->
			resultProvider.evaluate(createEvaluationContext(tree))
		}

		assert context.getVariableNames(Context.RESULTS).contains('vehicle')
		assert context.getVariable(Context.RESULTS, 'vehicle') == 'bicycle'

		context.setVariable(Context.INFORMATION, 'age', 19)

		resultProviders.each {resultProvider ->
			resultProvider.evaluate(createEvaluationContext(tree))
		}

		assert context.getVariableNames(Context.RESULTS).contains('vehicle')
		assert context.getVariable(Context.RESULTS, 'vehicle') == 'car'
	}

	@Test
	void treeWithCopyingRulesShouldBeParseable() {
		Tree tree = parseTree('copying_rule')
		assert tree != null
		assert tree.code == 'copying rule'

		List resultProviders = tree.getResultProviders()
		assert resultProviders != null
		assert resultProviders.size() == 1

		context.setVariable(Context.INFORMATION, 'original', 'watch')

		resultProviders.each {resultProvider ->
			resultProvider.evaluate(createEvaluationContext(tree))
		}

		assert context.getVariableNames(Context.RESULTS).contains('copy')
		assert context.getVariable(Context.RESULTS, 'copy') == 'watch'
	}

	@Test
	void treeWithResultFromDictionaryShouldBeParseable() {
		Tree tree = parseTree('result_from_dictionary')
		assert tree != null
		assert tree.code == 'result from dictionary'

		List resultProviders = tree.getResultProviders()
		assert resultProviders != null
		assert resultProviders.size() == 4

		context.setVariable(Context.INFORMATION, 'season', 'summer')

		resultProviders.each {resultProvider ->
			resultProvider.evaluate(createEvaluationContext(tree))
		}

		assert context.getVariableNames(Context.RESULTS).contains('description')
		assert context.getVariable(Context.RESULTS, 'description') == 'It\'s hot!'
	}

	@Test
	void compositeTreeShouldBeParseable() {
		Tree tree = parseTree('include')
		assert tree != null
		assert tree.code == 'include'

		List nodeCodes = tree.getNodeCodes()
		assert nodeCodes != null
		assert nodeCodes.size() == 1

		EvaluationContext evaluationContext = createEvaluationContext(tree)

		def form = tree.findNode(nodeCodes[0]).evaluate(evaluationContext, false)
		assert form != null

		def formElements = form.getElements()
		assert formElements != null
		assert formElements.size() == 1

		def formElement = formElements[0]
		assert formElement.name == 'age'
		assert formElement.type != null
		assert formElement.type.name == 'number'
		assert !formElement.type.list

		assert formElement.texts != null
		assert formElement.texts.size() == 1
		assert formElement.texts['label'] == 'What\'s your age?'

		List resultProviders = tree.getResultProviders()
		assert resultProviders != null
		assert resultProviders.size() == 2

		context.setVariable(Context.INFORMATION, 'age', 16)

		resultProviders.each {resultProvider ->
			resultProvider.evaluate(evaluationContext)
		}

		assert context.getVariableNames(Context.RESULTS).contains('votingAllowed')
		assert !context.getVariable(Context.RESULTS, 'votingAllowed')
	}

	@Test
	void compositeTreeWithDynamicPropertyShouldBeParseable() {
		Tree tree = parseTree('include_with_dynamic_property')
		assert tree != null
		assert tree.code == 'include'

		List nodeCodes = tree.getNodeCodes()
		assert nodeCodes != null
		assert nodeCodes.size() == 1

		def dynamicProperties = tree.findNode(nodeCodes[0])?.dynamicProperties
		assert dynamicProperties != null
		assert dynamicProperties.size() == 1
		assert dynamicProperties.containsKey('included')
		assert dynamicProperties['included']
	}

	private Tree parseTree(String name) {
		return parser.parse(repository.locate(name).tree)
	}

	private EvaluationContext createEvaluationContext(Tree tree, ExternalValueProvider... externalValueProviders) {
		EvaluationContextBuilder evaluationContextBuilder = new EvaluationContextBuilder()
		evaluationContextBuilder.setContext(context)
		evaluationContextBuilder.setDictionary(tree.getDictionary())

		externalValueProviders.each {
			evaluationContextBuilder.addExternalValueProvider(it)
		}

		evaluationContextBuilder.build()
	}
}
