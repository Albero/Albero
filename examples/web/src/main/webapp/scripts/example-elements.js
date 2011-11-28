if (!example) {
	var example = {};
}

example.elements = function() {
	function DataList(descriptor, parameters) {
		this.data = [];

		this.keyProperty = parameters['key'];
		this.groupProperty = parameters['group'];
		this.synonymsProperty = parameters['synonyms'];
		this.availableProperty = parameters['available'];
		this.descriptionProperty = parameters['description'];

		for (var i = 0; i < parameters.information[parameters.data].length; i++) {
			this.data.push({
				value: parameters.information[parameters.data][i],
				found: false,
				selected: false
			});
		}

		this.label = document.createElement('label');
		this.label.innerHTML = descriptor.renderedTexts['label'];

		this.searchField = document.createElement('input');

		this.searchButton = document.createElement('button');
		this.searchButton.appendChild(document.createTextNode('Zoeken'));

		this.resultsList = document.createElement('select');
		this.resultsList.size = 10;

		this.addButton = document.createElement('button');
		this.addButton.appendChild(document.createTextNode('Toevoegen'));

		this.descriptionLabel = document.createElement('label');

		this.selectionList = document.createElement('select');
		this.selectionList.size = 10;

		this.removeButton = document.createElement('button');
		this.removeButton.appendChild(document.createTextNode('Verwijderen'));
	}

	DataList.prototype = {
		initialise: function() {
			var utilities = nl.trivento.albero.Utilities;

			this.searchListener = nl.trivento.albero.Utilities.addEventListener(
				this.searchButton, 'click', nl.trivento.albero.Utilities.bind(this, this.search));
			this.resultsListener = nl.trivento.albero.Utilities.addEventListener(
				this.resultsList, 'change', nl.trivento.albero.Utilities.bind(this, this.resultsSelectionChanged));
			this.addListener = nl.trivento.albero.Utilities.addEventListener(
				this.addButton, 'click', nl.trivento.albero.Utilities.bind(this, this.add));
			this.selectionListener = nl.trivento.albero.Utilities.addEventListener(
				this.selectionList, 'change', nl.trivento.albero.Utilities.bind(this, this.selectionSelectionChanged));
			this.removeListener = nl.trivento.albero.Utilities.addEventListener(
				this.removeButton, 'click', nl.trivento.albero.Utilities.bind(this, this.remove));

			this.performSearch('');
			this.enableOrDisableAddButton();
			this.enableOrDisableRemoveButton();
		},

		destroy: function() {
			var utilities = nl.trivento.albero.Utilities;

			nl.trivento.albero.Utilities.removeEventListener(this.searchButton, 'click', this.searchListener);
			nl.trivento.albero.Utilities.removeEventListener(this.resultsList, 'change', this.resultsListener);
			nl.trivento.albero.Utilities.removeEventListener(this.addButton, 'click', this.addListener);
			nl.trivento.albero.Utilities.removeEventListener(this.selectionList, 'change', this.selectionListener);
			nl.trivento.albero.Utilities.removeEventListener(this.removeButton, 'click', this.removeListener);
		},

		addTo: function(container) {
			var searchBlock = document.createElement('div');
			searchBlock.appendChild(this.searchField);
			searchBlock.appendChild(this.searchButton);

			var resultsBlock = document.createElement('div');
			resultsBlock.appendChild(this.resultsList);
			resultsBlock.appendChild(this.addButton);

			var descriptionBlock = document.createElement('div');
			descriptionBlock.appendChild(this.descriptionLabel);

			var selectionBlock = document.createElement('div');
			selectionBlock.appendChild(this.selectionList);
			selectionBlock.appendChild(this.removeButton);

			container.appendChild(this.label);
			container.appendChild(searchBlock);
			container.appendChild(resultsBlock);
			container.appendChild(descriptionBlock);
			container.appendChild(selectionBlock);
		},

		handleValidationErrors: function(validationErrors) {
			alert(validationErrors.join(', ')); // TODO
		},

		getLabel: function() {
			return this.label;
		},

		getValue: function() {
			var value = [];

			for (var i = 0; i < this.data.length; i++) {
				var element = this.data[i];

				if (element.selected) {
					value.push(element.value);
				}
			}

			return value;
		},

		setValue: function(value) {
			for (var i = 0; i < value.length; i++) {
				for (var j = 0; j < this.data.length; j++) {
					if (this.getElementValue(this.data[j]) == this.getElementValue(value[i])) {
						this.data[j].selected = true;
					}
				}
			}

			this.synchroniseSelectionList();
		},

		resultsSelectionChanged: function() {
			this.updateDescription();

			this.enableOrDisableAddButton();
		},

		updateDescription: function() {
			nl.trivento.albero.Utilities.clear(this.descriptionLabel);

			var element = this.getSelectedElement(this.resultsList);

			if (element) {
				this.descriptionLabel.appendChild(document.createTextNode(this.getElementDescription(element)));
			}
		},

		selectionSelectionChanged: function() {
			this.enableOrDisableRemoveButton();
		},

		search: function() {
			this.performSearch(this.searchField.value);
		},

		performSearch: function(searchText) {
			var matches = function(text) {
				return text && (text.toLowerCase().indexOf(searchText.toLowerCase()) >= 0);
			};

			for (var i = 0; i < this.data.length; i++) {
				var element = this.data[i];

				element.found = matches(this.getElementValue(element)) || matches(this.getElementGroup(element));

				var synonyms = this.getElementSynonyms(element);
				var j = 0;

				while ((j < synonyms.length) && !element.found) {
					element.found = matches(synonyms[j]);

					j++;
				}
			}

			this.synchroniseResultsList();
		},

		add: function() {
			this.getSelectedElement(this.resultsList).selected = true;

			this.synchroniseResultsList();
			this.synchroniseSelectionList();
		},

		remove: function() {
			this.getSelectedElement(this.selectionList).selected = false;

			this.synchroniseResultsList();
			this.synchroniseSelectionList();
		},

		synchroniseResultsList: function() {
			this.synchroniseList(this.resultsList, function(element) {
				return element.found && !element.selected;
			});

			this.updateDescription();
			this.enableOrDisableAddButton();
		},

		synchroniseSelectionList: function() {
			this.synchroniseList(this.selectionList, function(element) {
				return element.selected;
			});

			this.enableOrDisableRemoveButton();
		},

		synchroniseList: function(list, test) {
			nl.trivento.albero.Utilities.clear(list);

			for (var i = 0; i < this.data.length; i++) {
				var element = this.data[i];

				if (test(element)) {
					var elementValue = this.getElementValue(element);
					var elementSynonyms = this.getElementSynonyms(element);

					var option = document.createElement('option');
					option.value = elementValue;
					option.appendChild(document.createTextNode(elementValue +
						((elementSynonyms.length == 0) ? '' : (' (' + elementSynonyms.join(', ') + ')'))));

					list.appendChild(option);
				}
			}
		},

		enableOrDisableAddButton: function() {
			var test = nl.trivento.albero.Utilities.bind(this, function(element) {
				return this.isElementAvailable(element);
			});

			this.enableOrDisableButton(this.resultsList, this.addButton, test);
		},

		enableOrDisableRemoveButton: function() {
			this.enableOrDisableButton(this.selectionList, this.removeButton);
		},

		enableOrDisableButton: function(list, button, test) {
			if ((list.selectedIndex == -1) || (test && !test(this.getSelectedElement(list)))) {
				button.disabled = 'disabled';
			} else if (button.hasAttribute ? button.hasAttribute('disabled') : button.getAttribute('disabled')) {
				button.removeAttribute('disabled');
			}
		},

		getSelectedElement: function(list) {
			var i = 0;

			while ((i < this.data.length) && (this.getElementValue(this.data[i]) != list.value)) {
				i++;
			}

			return (i == this.data.length) ? null : this.data[i];
		},

		getElementValue: function(element) {
			return this.keyProperty ? element.value[this.keyProperty] : element.value;
		},

		getElementGroup: function(element) {
			return this.groupProperty ? element.value[this.groupProperty] : null;
		},

		getElementDescription: function(element) {
			var descriptionAvailable = element.value.hasOwnProperty(this.descriptionProperty);

			return descriptionAvailable ? element.value[this.descriptionProperty] : this.getElementValue(element);
		},

		isElementAvailable: function(element) {
			return !this.availableProperty ||
				!element.value.hasOwnProperty(this.availableProperty) || element.value[this.availableProperty];
		},

		getElementSynonyms: function(element) {
			var synonyms = this.synonymsProperty ? element.value[this.synonymsProperty] : null;

			return synonyms || [];
		}
	};

	return {
		DataList: DataList
	};
}();
