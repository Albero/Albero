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
package nl.trivento.albero.parsers;

import static nl.trivento.albero.model.Context.INFORMATION;
import static nl.trivento.albero.model.Context.RESULTS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nl.trivento.albero.configuration.Configuration;
import nl.trivento.albero.model.Context;
import nl.trivento.albero.model.EvaluationContext;
import nl.trivento.albero.model.Node;
import nl.trivento.albero.model.ResultProvider;
import nl.trivento.albero.model.Tree;
import nl.trivento.albero.model.model.DefaultModel;
import nl.trivento.albero.model.model.Model;
import nl.trivento.albero.model.model.Property;
import nl.trivento.albero.model.model.SimplePropertyType;
import nl.trivento.albero.model.questions.QuestionNode;
import nl.trivento.albero.model.questions.SimpleQuestion;
import nl.trivento.albero.model.text.DefaultDictionary;
import nl.trivento.albero.model.text.Dictionary;
import nl.trivento.albero.parsers.ParseException;
import nl.trivento.albero.parsers.Parser;

/**
 * A parser that returns an in-memory tree based on a code. It contains the following trees:
 * <dl>
 * <dt>discount</dt>
 * <dd>A decision tree that determines the discount percentage for an order.</dd>
 * </dl>
 *
 */
public class CodeParser implements Parser {
	private Map<String, Tree> trees;

	/**
	 * Creates a code parser.
	 */
	public CodeParser() {}

	public void initialise(Configuration configuration, Map<String, String> parameters) {
		trees = new HashMap<String, Tree>();

		trees.put("discount", new DiscountTree());
	}

	/**
	 * A discount tree:
	 * <pre>
	 *        +------------------+
	 *        | First time here? |
	 *        +------------------+
	 *            /          \
	 *       yes /            \ no
	 *          /              \
	 *  +--------------+    +-----+
	 *  | items bought |    |  0% |
	 *  +--------------+    +-----+
	 *      /      \
	 * < 5 /        \ >= 5
	 *    /          \
	 * +-----+    +-----+
	 * |  5% |    | 10% |
	 * +-----+    +-----+
	 * </pre>
	 *
	 */
	private static class DiscountTree implements Tree {
		private Dictionary dictionary;

		private DefaultModel model;

		private Map<String, Node> nodes;

		private DefaultModel resultModel;
		private List<ResultProvider> resultProviders;

		/**
		 * Creates a discount tree.
		 */
		public DiscountTree() {
			dictionary = new DefaultDictionary();

			model = new DefaultModel();
			model.addProperty(new Property("first_visit", new SimplePropertyType("boolean")));
			model.addProperty(new Property("items_bought", new SimplePropertyType("number")));

			nodes = new LinkedHashMap<String, Node>();
			addNode(new QuestionNode("first_visit", null, new SimpleQuestion(model, "first_visit")));
			addNode(new QuestionNode("items_bought", null, new SimpleQuestion(model, "items_bought")));

			resultModel = new DefaultModel();
			resultModel.addProperty(new Property("discount", new SimplePropertyType("number")));

			resultProviders = new ArrayList<ResultProvider>();
			resultProviders.add(new ResultProvider() {
				public void evaluate(EvaluationContext evaluationContext) {
					Context context = evaluationContext.getContext();

					if (context.getVariableNames(INFORMATION).contains("first_visit") &&
							!((Boolean) context.getVariable(INFORMATION, "first_visit")).booleanValue()) {
						context.setVariable(RESULTS, "discount", 0);
					}
				}
			});
			resultProviders.add(new ResultProvider() {
				public void evaluate(EvaluationContext evaluationContext) {
					Context context = evaluationContext.getContext();

					if (context.getVariableNames(INFORMATION).contains("first_visit") &&
							((Boolean) context.getVariable(INFORMATION, "first_visit")).booleanValue() &&
							context.getVariableNames(INFORMATION).contains("items_bought") &&
							((Integer) context.getVariable(INFORMATION, "items_bought")).intValue() < 5) {
						context.setVariable(RESULTS, "discount", 5);
					}
				}
			});
			resultProviders.add(new ResultProvider() {
				public void evaluate(EvaluationContext evaluationContext) {
					Context context = evaluationContext.getContext();

					if (context.getVariableNames(INFORMATION).contains("first_visit") &&
							((Boolean) context.getVariable(INFORMATION, "first_visit")).booleanValue() &&
							context.getVariableNames(INFORMATION).contains("items_bought") &&
							((Integer) context.getVariable(INFORMATION, "items_bought")).intValue() >= 5) {
						context.setVariable(RESULTS, "discount", 10);
					}
				}
			});
		}

		private void addNode(Node node) {
			nodes.put(node.getCode(), node);
		}

		public Dictionary getDictionary() {
			return dictionary;
		}

		public Model getModel() {
			return model;
		}

		public String getCode() {
			return "discount";
		}

		public List<String> getNodeCodes() {
			return new LinkedList<String>(nodes.keySet());
		}

		public Node findNode(String code) {
			return nodes.get(code);
		}

		public Model getResultModel() {
			return resultModel;
		}

		public List<ResultProvider> getResultProviders() {
			return Collections.unmodifiableList(resultProviders);
		}
	}

	public void destroy() {
		trees = null;
	}

	public String getName() {
		return NAME;
	}

	public Tree parse(String tree) throws ParseException {
		if (!trees.containsKey(tree)) {
			throw new ParseException("unknown tree: '", tree, "'");
		}

		return trees.get(tree);
	}

	/** The name of this parser: {@value}. */
	public final static String NAME = "code";
}
