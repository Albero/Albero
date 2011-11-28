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
package nl.profict.albero;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import nl.profict.albero.configuration.Configuration;
import nl.profict.albero.extensions.ExtensionProvider;
import nl.profict.albero.model.Context;
import nl.profict.albero.model.EvaluationContext;
import nl.profict.albero.model.EvaluationException;
import nl.profict.albero.model.Node;
import nl.profict.albero.model.ResultProvider;
import nl.profict.albero.model.Tree;
import nl.profict.albero.model.builders.EvaluationContextBuilder;
import nl.profict.albero.model.external.ExternalValueProvider;
import nl.profict.albero.model.forms.Form;
import nl.profict.albero.parsers.ParseException;
import nl.profict.albero.parsers.Parser;
import nl.profict.albero.repositories.RepositoryException;
import nl.profict.albero.repositories.TreeInformation;
import nl.profict.albero.traversal.TraversalStrategy;
import nl.profict.albero.utilities.Logger;

/**
 * The Albero engine.
 *
 * @author levi_h
 */
public class Engine {
	private Configuration configuration;

	private final Logger logger;

	/**
	 * Creates an engine.
	 *
	 * @param configuration the configuration to use
	 */
	public Engine(Configuration configuration) {
		this.configuration = configuration;

		logger = Logger.get(getClass());
	}

	/**
	 * Evaluates a context.
	 *
	 * @param context the context to evaluate
	 * @return the result of the evaluation
	 * @throws AlberoException when the context can't be evaluated
	 */
	public Form evaluate(Context context) throws AlberoException {
		if (!context.getVariableNames(Context.ALBERO).contains("role")) {
			throw new AlberoException("context variable 'role' is required");
		}

		TreeInformation information = getTreeInformation(getContextVariable(context, "tree"));
		Parser parser = getParser(information.getParser());
		Tree tree = parseTree(information.getTree(), parser);

		TraversalStrategy traversalStrategy = getTraversalStrategy(getContextVariable(context, "traversal_strategy"));

		Form form;

		EvaluationContext evaluationContext = createEvaluationContext(tree, context);

		if (context.getVariableNames(Context.ALBERO).contains("node")) {
			Node node = getNode(tree, getContextVariable(context, "node"));
			boolean validate = !context.getVariableNames(Context.ALBERO).contains("revisit");

			form = evaluateNode(tree, node, traversalStrategy, evaluationContext, validate);
		} else {
			form = evaluateNode(tree, traversalStrategy, evaluationContext);
		}

		return form;
	}

	private String getContextVariable(Context context, String name) throws AlberoException {
		Object value = context.getVariable(Context.ALBERO, name);

		if (value instanceof String) {
			return (String) value;
		} else {
			throw new AlberoException("context variable '", name, "' is of the wrong type");
		}
	}

	private TreeInformation getTreeInformation(String code) throws RepositoryException {
		logger.debug("locating tree information for code '", code, "'");

		TreeInformation information = configuration.getRepository().locate(code);

		logger.debug("found information: ", information);

		return information;
	}

	private Parser getParser(String name) throws AlberoException {
		logger.debug("using parser '", name, "'");

		Parser parser = configuration.getParsers().get(name);

		if (parser == null) {
			throw new AlberoException("unknown parser: '", name, "' ",
				"(available parsers: ", configuration.getParsers(), ")");
		}

		logger.debug("found parser: ", parser);

		return parser;
	}

	private Tree parseTree(String tree, Parser parser) throws ParseException {
		logger.debug("parsing tree ", tree);

		Tree parsedTree = parser.parse(tree);

		logger.debug("parsed tree: ", parsedTree);

		return parsedTree;
	}

	private TraversalStrategy getTraversalStrategy(String name) {
		logger.debug("using traversal strategy '", name, "'");

		TraversalStrategy traversalStrategy = configuration.getTraversalStrategies().get(name);

		if (traversalStrategy == null) {
			throw new AlberoException("unknown traversal strategy: '", name, "' ",
				"(available traversal strategies: ", configuration.getTraversalStrategies(), ")");
		}

		logger.debug("found traversal strategy: ", traversalStrategy);

		return traversalStrategy;
	}

	private Node getNode(Tree tree, String code) throws AlberoException {
		Node node;

		if (code == null) {
			node = null;
		} else {
			logger.debug("trying to find node with code '", code, "' in tree ", tree);

			node = tree.findNode(code);

			if (node == null) {
				throw new AlberoException("no node with code '", code, "' found in tree ", tree);
			}

			logger.debug("found node: ", node);
		}

		return node;
	}

	private Form evaluateNode(Tree tree, TraversalStrategy traversalStrategy,
			EvaluationContext evaluationContext) throws EvaluationException {
		Form form;

		Context context = evaluationContext.getContext();

		Node node = getNode(tree, traversalStrategy.getNode(tree, context));

		if (node == null) {
			Set<String> alberoVariables = context.getVariableNames(Context.ALBERO);

			for (String variable: Arrays.asList("node", "node group")) {
				if (alberoVariables.contains(variable)) {
					context.removeVariable(Context.ALBERO, variable);
				}
			}

			setNodeGroups(evaluationContext, tree);
			provideResults(evaluationContext, tree);

			form = null;
		} else {
			form = evaluateNode(tree, node, traversalStrategy, evaluationContext, false);
		}

		return form;
	}

	private Form evaluateNode(Tree tree, Node node, TraversalStrategy traversalStrategy,
			EvaluationContext evaluationContext, boolean validate) throws EvaluationException {
		Form form = node.evaluate(evaluationContext, validate);

		Context context = evaluationContext.getContext();
		context.setVariable(Context.ALBERO, "node", node.getCode());
		context.setVariable(Context.ALBERO, "node group", node.getGroup());

		setNodeGroups(evaluationContext, tree);
		provideResults(evaluationContext, tree);

		if (form == null) {
			form = evaluateNode(tree, traversalStrategy, evaluationContext);
		}

		return form;
	}

	private EvaluationContext createEvaluationContext(Tree tree, Context context) {
		EvaluationContextBuilder builder = new EvaluationContextBuilder();
		builder.setContext(context);
		builder.setDictionary(tree.getDictionary());

		for (ExtensionProvider extensionProvider: configuration.getExtensionProviders()) {
			for (ExternalValueProvider externalValueProvider: extensionProvider.getExternalValueProviders()) {
				builder.addExternalValueProvider(externalValueProvider);
			}
		}

		return builder.build();
	}

	private void setNodeGroups(EvaluationContext evaluationContext, Tree tree) {
		Set<String> nodeGroups = new LinkedHashSet<String>();

		for (String nodeCode: tree.getNodeCodes()) {
			nodeGroups.add(tree.findNode(nodeCode).getGroup());
		}

		evaluationContext.getContext().setVariable(Context.ALBERO, "node groups", new LinkedList<String>(nodeGroups));
	}

	private void provideResults(EvaluationContext evaluationContext, Tree tree) throws EvaluationException {
		for (ResultProvider resultProvider: tree.getResultProviders()) {
			resultProvider.evaluate(evaluationContext);
		}
	}
}
