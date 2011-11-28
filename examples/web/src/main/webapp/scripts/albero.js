if (!nl) {
	var nl = {};
}

if (!nl.trivento) {
	nl.trivento = {};
}

nl.trivento.albero = function() {
	function Albero(engineLocation, tree, role, traversalStrategy, information, elementId) {
		this.elementFactories = {};
		this.elements = [];

		this.engineLocation = engineLocation;

		this.context = {
			'albero': {
				'tree': tree,
				'role': role,
				'traversal_strategy': traversalStrategy
			},

			'information': information
		};
		this.results = {};

		this.nodeGroups = [];
		this.nodes = {};
		this.formInformation = {};

		this.formGuard = function(albero, render) {
			render();
		};
		this.listeners = [];

		this.textRenderer = function(text) {
			return text;
		};

		this.element = document.getElementById(elementId);
	}

	Albero.prototype = {
		addElementFactory: function(name, factory) {
			this.elementFactories[name] = factory;
		},

		setFormGuard: function(formGuard) {
			this.formGuard = formGuard;
		},

		setTextRenderer: function(textRenderer) {
			this.textRenderer = textRenderer;
		},

		addListener: function(listener) {
			this.listeners.push(listener);
		},

		notifyListeners: function(callback, arguments) {
			for (var i = 0; i < this.listeners.length; i++) {
				var listener = this.listeners[i];

				if (listener.hasOwnProperty(callback)) {
					listener[callback].apply(null, arguments);
				}
			}
		},

		revisit: function(nodeGroup, node) {
			this.elements = [];

			this.context['albero']['node group'] = nodeGroup;
			this.context['albero']['node'] = node;

			this.updateNodes();
			this.updateNodeGroups();

			this.context['albero']['revisit'] = true;
			this.refresh();
		},

		refresh: function() {
			var request = new XMLHttpRequest();
			request.onreadystatechange = Utilities.bind(this, function() {
				if (request.readyState == 4) {
					if (request.status == 200) {
						var response = JSON.parse(request.responseText);

						this.context = response.context;
						this.processResults();
						this.replaceForm(response.form);
					} else {
						this.clearForm();

						this.element.appendChild(document.createTextNode(request.statusText));
					}
				}
			});
			request.open('GET', this.engineLocation + '?context=' + JSON.stringify(this.context), true);
			request.send(null);
		},

		clearForm: function() {
			Utilities.clear(this.element);
		},

		replaceForm: function(formDescriptor) {
			this.clearForm();

			if (formDescriptor) {
				this.updateNodes();
				this.updateNodeGroups();

				this.formGuard(this, Utilities.bind(this, function() {
					this.element.appendChild(this.createForm(formDescriptor));
				}));
			} else {
				this.notifyListeners('onComplete', []);
			}
		},

		createForm: function(formDescriptor) {
			var addForm = Utilities.bind(this, function(parent, formDescriptor, addForm, addElement) {
				renderTexts(formDescriptor);

				var block = document.createElement('div');
				block.setAttribute('class', 'form');

				addText(block, formDescriptor, 'introduction');

				if (formDescriptor.forms) {
					for (var i = 0; i < formDescriptor.forms.length; i++) {
						addForm(block, formDescriptor.forms[i], addForm, addElement);
					}
				} else if (formDescriptor.elements) {
					for (var i = 0; i < formDescriptor.elements.length; i++) {
						var elementDescriptor = formDescriptor.elements[i];

						renderTexts(elementDescriptor);

						addText(block, elementDescriptor, 'introduction');
						addElement(block, elementDescriptor);
						addText(block, elementDescriptor, 'toolTip');
					}
				}

				parent.appendChild(block);
			});
			var renderTexts = Utilities.bind(this, function(descriptor) {
				descriptor.renderedTexts = {};

				for (var type in descriptor.texts) {
					descriptor.renderedTexts[type] = this.textRenderer(descriptor.texts[type]);
				}
			});
			var addText = Utilities.bind(this, function(parent, descriptor, type) {
				if (descriptor.renderedTexts[type]) {
					var textBlock = document.createElement('div');
					textBlock.setAttribute('class', type);

					textBlock.innerHTML = descriptor.renderedTexts[type];

					parent.appendChild(textBlock);
				}
			});
			var addElement = Utilities.bind(this, function(parent, elementDescriptor) {
				var name = null;
				var parameters = {
					information: this.context['information']
				};

				if (elementDescriptor.hasOwnProperty('renderingHint')) {
					var renderingHintAndParameters = elementDescriptor.renderingHint.split(/\s*,\s*/);

					if (this.elementFactories.hasOwnProperty(renderingHintAndParameters[0])) {
						name = renderingHintAndParameters[0];

						for (var i = 1; i < renderingHintAndParameters.length; i++) {
							var parameterNameAndValue = renderingHintAndParameters[i].split(/\s*=\s*/)

							parameters[parameterNameAndValue[0]] = parameterNameAndValue[1];
						}
					}
				}

				if (name == null) {
					if (elementDescriptor.type.name == 'boolean') {
						name = 'check box';
					} else if (elementDescriptor.hasOwnProperty('options')) {
						name = 'combo box';
					} else {
						name = 'text field';
					}
				}

				var element = new this.elementFactories[name](elementDescriptor, parameters);

				if (typeof element.initialise == 'function') {
					element.initialise();
				}
				
				if (this.context.hasOwnProperty('information') &&
						this.context.information.hasOwnProperty(elementDescriptor.name)) {
					element.setValue(this.context.information[elementDescriptor.name]);
				}

				if (elementDescriptor.hasOwnProperty('validationErrors') &&
						(typeof element.handleValidationErrors == 'function')) {
					element.handleValidationErrors(elementDescriptor['validationErrors']);
				}

				this.elements.push({
					descriptor: elementDescriptor,
					element: element
				});

				var block = document.createElement('div');
				block.setAttribute('class', 'element');
				element.addTo(block);
				parent.appendChild(block);
			});

			var form = document.createElement('form');
			addForm(form, formDescriptor, addForm, addElement);

			var submitBlock = document.createElement('div');
			submitBlock.setAttribute('class', 'submit');

			var submit = document.createElement('button');
			submit.setAttribute('type', 'submit');
			submitBlock.appendChild(submit);

			form.appendChild(submitBlock);

			Utilities.addEventListener(form, 'submit', Utilities.bind(this, this.handleSubmit));
			Utilities.addEventListener(submit, 'click', Utilities.bind(this, this.handleSubmit));

			this.notifyListeners('onForm', []);

			return form;
		},

		handleSubmit: function() {
			var formInformation = [];

			for (var i = 0; i < this.elements.length; i++) {
				if (!this.context.hasOwnProperty('information')) {
					this.context['information'] = {};
				}

				var value = this.elements[i].element.getValue();

				if (this.elements[i].descriptor.type.name == 'number') {
					if (value.constructor == Array) {
						for (var i = 0; i < value.length; i++) {
							value[i] = parseInt(value[i]);
						}
					} else {
						value = parseInt(value);
					}
				}

				this.context['information'][this.elements[i].descriptor.name] = value;

				formInformation.push({
					variable: this.elements[i].descriptor.name,
					label: this.elements[i].element.getLabel().innerHTML,
					value: value
				});

				if (typeof this.elements[i].element.destroy == 'function') {
					this.elements[i].element.destroy();
				}
			}

			this.elements = [];
			this.notifyListeners('onInformation', [this.context['information']]);

			this.formInformation[this.context['albero']['node']] = formInformation;

			this.refresh();
		},

		updateNodeGroups: function() {
			var newNodeGroups = [];

			var nodeGroup = this.context['albero']['node group'];

			var i = 0;

			while ((this.nodeGroups[i] != nodeGroup) && (i < this.nodeGroups.length)) {
				newNodeGroups.push(this.nodeGroups[i]);

				i++;
			}

			newNodeGroups.push(nodeGroup);

			this.nodeGroups = newNodeGroups;
		},

		updateNodes: function() {
			var newNodes = {};

			var nodeGroup = this.getNodeGroup();
			var node = this.context.albero['node'];

			var add = true;

			for (var i = 0; i < this.nodeGroups.length; i++) {
				var currentNodeGroup = this.nodeGroups[i];

				if (add) {
					newNodes[currentNodeGroup] = [];
				}

				var nodes = this.nodes[currentNodeGroup];

				for (var j = 0; j < nodes.length; j++) {
					var currentNode = nodes[j];

					add = add && (currentNode != node);

					if (add) {
						newNodes[currentNodeGroup].push(currentNode);
					} else if (this.formInformation.hasOwnProperty(currentNode)) {
						var formInformation = this.formInformation[currentNode];

						for (var k = 0; k < formInformation.length; k++) {
							delete this.context['information'][formInformation[k].variable];
						}

						delete this.formInformation[currentNode];
					}
				}
			}

			if (!newNodes.hasOwnProperty(nodeGroup)) {
				newNodes[nodeGroup] = [];
			}

			newNodes[nodeGroup].push(node); 

			this.nodes = newNodes;
		},

		processResults: function() {
			if (this.context.hasOwnProperty('results')) {
				for (var resultName in this.context.results) {
					if (!this.results.hasOwnProperty(resultName)) {
						var result = this.context.results[resultName];
						result = this.textRenderer(result);
						this.results[resultName] = result;
						this.context.results = this.results
						this.notifyListeners('onResult', [resultName, result]);
					}
				}
			}
		},

		getNodeGroups: function() {
			return this.context['albero']['node groups'];
		},

		getNodeGroup: function() {
			return this.context['albero']['node group'];
		},

		getNodes: function(nodeGroup) {
			return this.nodes.hasOwnProperty(nodeGroup) ? this.nodes[nodeGroup] : [];
		},

		getFormInformation: function(node) {
			return this.formInformation[node];
		},

		getResults: function() {
			return this.context['results'] || {};
		}
	};

	Utilities = {
		bind: function(thisObject, boundFunction) {
			return function() {
				return boundFunction.apply(thisObject, arguments);
			};
		},

		addEventListener: function(element, type, listener) {
			var listenerWrapper = function(event) {
				if (window.event) {
					window.event.returnValue = false;
				} else {
					event.preventDefault(event);
				}

				listener();
			};

			if (element.attachEvent) {
				element.attachEvent('on' + type, listenerWrapper);
			} else {
				element.addEventListener(type, listenerWrapper, false);
			}

			return listenerWrapper;
		},

		removeEventListener: function(element, type, listenerWrapper) {
			if (element.detachEvent) {
				element.detachEvent('on' + type, listenerWrapper);
			} else {
				element.removeEventListener(type, listenerWrapper, false);
			}
		},

		clear: function(container) {
			while (container.childNodes.length > 0) {
				container.removeChild(container.firstChild);
			}
		}
	};

	return {
		Albero: Albero,
		Utilities: Utilities
	};
}();
