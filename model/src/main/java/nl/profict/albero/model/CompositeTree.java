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
package nl.profict.albero.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nl.profict.albero.model.forms.Form;
import nl.profict.albero.model.model.Model;
import nl.profict.albero.model.model.ModelException;
import nl.profict.albero.model.model.Property;
import nl.profict.albero.model.text.Dictionary;
import nl.profict.albero.utilities.StringUtilities;

/**
 * A tree that consists of other trees.
 *
 */
public class CompositeTree implements Tree {
	private String code;

	private Map<String, Tree> trees;

	/**
	 * Creates a composite tree.
	 *
	 * @param code the code of the tree
	 */
	public CompositeTree(String code) {
		this.code = code;

		trees = new HashMap<String, Tree>();
	}

	/**
	 * Adds a tree (or replaces one, if its code matches the code of a previously added tree).
	 *
	 * @param tree the tree to add
	 */
	public void addTree(Tree tree) {
		trees.put(tree.getCode(), new TreeWrapper(tree));
	}

	public String getCode() {
		return code;
	}

	public Dictionary getDictionary() {
		CompositeDictionary dictionary = new CompositeDictionary();

		for (Tree tree: trees.values()) {
			dictionary.addDictionary(tree.getDictionary());
		}

		return dictionary;
	}

	public Model getModel() {
		CompositeModel model = new CompositeModel();

		for (Tree tree: trees.values()) {
			model.addModel(tree.getModel());
		}

		return model;
	}

	public List<String> getNodeCodes() {
		List<String> nodeCodes = new ArrayList<String>();

		for (Tree tree: trees.values()) {
			nodeCodes.addAll(tree.getNodeCodes());
		}

		return nodeCodes;
	}

	public Node findNode(String code) {
		Node node = null;

		int positionOfDot = code.indexOf('.');

		if (positionOfDot >= 0) {
			String treeCode = code.substring(0, positionOfDot);

			if (trees.containsKey(treeCode)) {
				node = trees.get(treeCode).findNode(code);
			}
		}

		return node;
	}

	public Model getResultModel() {
		CompositeModel resultModel = new CompositeModel();

		for (Tree tree: trees.values()) {
			resultModel.addModel(tree.getResultModel());
		}

		return resultModel;
	}

	public List<ResultProvider> getResultProviders() {
		List<ResultProvider> resultProviders = new LinkedList<ResultProvider>();

		for (Tree tree: trees.values()) {
			resultProviders.addAll(tree.getResultProviders());
		}

		return resultProviders;
	}

	private static class TreeWrapper implements Tree {
		private Tree tree;

		public TreeWrapper(Tree tree) {
			this.tree = tree;
		}

		public String getCode() {
			return tree.getCode();
		}

		public Dictionary getDictionary() {
			return tree.getDictionary();
		}

		public Model getModel() {
			return tree.getModel();
		}

		public List<String> getNodeCodes() {
			List<String> nodeCodes = new LinkedList<String>();

			for (String nodeCode: tree.getNodeCodes()) {
				nodeCodes.add(String.format("%s.%s", getCode(), nodeCode));
			}

			return nodeCodes;
		}

		public Node findNode(String code) {
			Node node = tree.findNode(code.substring(getCode().length() + 1));

			return (node == null) ? node : new NodeWrapper(tree, node);
		}

		public Model getResultModel() {
			return tree.getResultModel();
		}

		public List<ResultProvider> getResultProviders() {
			return tree.getResultProviders();
		}
	}

	private static class NodeWrapper implements Node {
		private Tree tree;
		private Node node;

		public NodeWrapper(Tree tree, Node node) {
			this.tree = tree;
			this.node = node;
		}

		public String getCode() {
			return String.format("%s.%s", tree.getCode(), node.getCode());
		}

		public String getGroup() {
			return node.getGroup();
		}

		public Map<String, ?> getDynamicProperties() {
			return node.getDynamicProperties();
		}

		public boolean isEvaluatable(Context context) {
			return node.isEvaluatable(context);
		}

		public Form evaluate(EvaluationContext evaluationContext, boolean validate) throws EvaluationException {
			return node.evaluate(evaluationContext, validate);
		}
	}

	private static class CompositeDictionary implements Dictionary {
		private List<Dictionary> dictionaries;

		public CompositeDictionary() {
			dictionaries = new LinkedList<Dictionary>();
		}

		public void addDictionary(Dictionary dictionary) {
			dictionaries.add(dictionary);
		}

		public boolean hasTranslation(String role, String key) {
			return findDictionary(role, key) != null;
		}

		public String findTranslation(String role, String key) {
			Dictionary dictionary = findDictionary(role, key);

			return (dictionary == null) ? key : dictionary.findTranslation(role, key);
		}

		public String findTranslation(String role, String key, Map<String, ?> parameters) {
			Dictionary dictionary = findDictionary(role, key);

			return (dictionary == null)
				? StringUtilities.interpolate(key, parameters)
				: dictionary.findTranslation(role, key, parameters);
		}

		private Dictionary findDictionary(String role, String key) {
			Iterator<Dictionary> it = dictionaries.iterator();
			Dictionary dictionary = null;

			while ((dictionary == null) && (it.hasNext())) {
				dictionary = it.next();

				if (!dictionary.hasTranslation(role, key)) {
					dictionary = null;
				}
			}

			return dictionary;
		}
	}

	private static class CompositeModel implements Model {
		private List<Model> models;

		public CompositeModel() {
			models = new LinkedList<Model>();
		}

		public void addModel(Model model) {
			models.add(model);
		}

		public boolean hasProperty(String path) {
			return findModel(path) != null;
		}

		public Property getProperty(String path) throws ModelException {
			Model model = findModel(path);

			if (model == null) {
				throw new ModelException("can't find property with path '", path, "'");
			} else {
				return model.getProperty(path);
			}
		}

		private Model findModel(String propertyPath) {
			Iterator<Model> it = models.iterator();
			Model model = null;

			while ((model == null) && it.hasNext()) {
				model = it.next();

				if (!model.hasProperty(propertyPath)) {
					model = null;
				}
			}

			return model;
		}
	}
}
