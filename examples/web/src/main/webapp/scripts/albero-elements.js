if (!nl) {
	var nl = {};
}

if (!nl.trivento) {
	nl.trivento = {};
}

if (!nl.trivento.albero) {
	nl.trivento.albero = {};
}

nl.trivento.albero.elements = function() {
	function TextField(descriptor) {
		this.label = document.createElement('label');
		this.label.setAttribute('class', 'inline');
		this.label.setAttribute('for', 'albero-' + descriptor.name);
		this.label.innerHTML = descriptor.renderedTexts['label'];

		this.textField = document.createElement('input');
		this.textField.id = 'albero-' + descriptor.name;
		this.textField.type = 'text';
		this.textField.name = descriptor.name;
	}

	TextField.prototype = {
		addTo: function(container) {
			container.appendChild(this.label);
			container.appendChild(this.textField);
		},

		handleValidationErrors: function(errors) {
			this.textField.setAttribute('class', 'error');
			this.textField.setAttribute('title', errors.join(' '));
		},

		getLabel: function() {
			return this.label;
		},

		getValue: function() {
			return this.textField.value;
		},

		setValue: function(value) {
			this.textField.value = value;
		}
	};

	function TextArea(descriptor) {
		this.label = document.createElement('label');
		this.label.setAttribute('class', 'inline');
		this.label.setAttribute('for', 'albero-' + descriptor.name);
		this.label.innerHTML = descriptor.renderedTexts['label'];

		this.textArea = document.createElement('textarea');
		this.textArea.id = 'albero-' + descriptor.name;
		this.textArea.name = descriptor.name;
	}

	TextArea.prototype = {
		addTo: function(container) {
			container.appendChild(this.label);
			container.appendChild(this.textArea);
		},

		handleValidationErrors: function(errors) {
			this.textArea.setAttribute('class', 'error');
			this.textArea.setAttribute('title', errors.join(' '));
		},

		getLabel: function() {
			return this.label;
		},

		getValue: function() {
			return this.textArea.value;
		},

		setValue: function(value) {
			this.textArea.value = value;
		}
	};

	function ComboBox(descriptor) {
		this.label = document.createElement('label');
		this.label.setAttribute('class', 'inline');
		this.label.setAttribute('for', 'albero-' + descriptor.name);
		this.label.innerHTML = descriptor.renderedTexts['label'];

		this.comboBox = document.createElement('select');
		this.comboBox.id = 'albero-' + descriptor.name;
		this.comboBox.name = descriptor.name;

		for (var i = 0; i < descriptor.options.length; i++) {
			var value = descriptor.options[i];

			var option = document.createElement('option');
			option.value = value;
			option.appendChild(document.createTextNode(value));

			this.comboBox.appendChild(option);
		}

		if (descriptor.type.list) {
			this.comboBox.setAttribute('multiple', 'multiple');
		}
	}

	ComboBox.prototype = {
		addTo: function(container) {
			container.appendChild(this.label);
			container.appendChild(this.comboBox);
		},

		getLabel: function() {
			return this.label;
		},

		getValue: function() {
			var value;

			if (this.comboBox.hasAttribute
					? this.comboBox.hasAttribute('multiple') : this.comboBox.getAttribute('multiple')) {
				value = [];

				for (var i = 0; i < this.comboBox.options.length; i++) {
					var option = this.comboBox.options[i];

					if (option.selected) {
						value.push(option.value);
					}
				}
			} else {
				value = this.comboBox.value;
			}

			return value;
		},

		setValue: function(value) {
			if (value.constructor == Array) {
				for (var i = 0; i < this.comboBox.options.length; i++) {
					for (var j = 0; j < value.length; j++) {
						if (this.comboBox.options[i].value == value[j]) {
							this.comboBox.options[i].selected = true;

							break;
						}
					}
				}
			} else {
				this.comboBox.value = value;
			}
		}
	};

	function RadioButtons(descriptor) {
		this.label = document.createElement('label');
		this.label.innerHTML = descriptor.renderedTexts['label'];

		this.radioButtons = []
		this.labels = []

		for (var i = 0; i < descriptor.options.length; i++) {
			var value = descriptor.options[i];

			var radioButton;

			try {
				radioButton = document.createElement('<input name="' + descriptor.name + '">');
			} catch (exception) {
				radioButton = document.createElement('input');
				radioButton.name = descriptor.name;
			}

			radioButton.id = 'albero-' + descriptor.name + '-' + i;
			radioButton.type = 'radio';
			radioButton.value = value;

			var label = document.createElement('label');
			label.appendChild(document.createTextNode(value));
			label.setAttribute('for', 'albero-' + descriptor.name + '-' + i);

			this.radioButtons.push(radioButton);
			this.labels.push(label);
		}
	}

	RadioButtons.prototype = {
		addTo: function(container) {
			container.appendChild(this.label);

			for (var i = 0; i < this.radioButtons.length; i++) {
				container.appendChild(this.radioButtons[i]);
				container.appendChild(this.labels[i]);
			}
		},

		handleValidationErrors: function(errors) {
			for (var i = 0; i < this.radioButtons.length; i++) {
				var radioButton = this.radioButtons[i];

				radioButton.setAttribute('class', 'error');
				radioButton.setAttribute('title', errors.join(' '));
			}
		},

		getLabel: function() {
			return this.label;
		},

		getValue: function() {
			var value = null;

			for (var i = 0; i < this.radioButtons.length; i++) {
				var radioButton = this.radioButtons[i];

				if (radioButton.checked) {
					value = radioButton.value;

					break;
				}
			}

			return value;
		},

		setValue: function(value) {
			for (var i = 0; i < this.radioButtons.length; i++) {
				var radioButton = this.radioButtons[i];

				if (radioButton.value == value) {
					radioButton.checked = true;

					break;
				}
			}
		}
	};

	function CheckBox(descriptor) {
		this.checkBox = document.createElement('input');
		this.checkBox.id = 'albero-' + descriptor.name;
		this.checkBox.type = 'checkbox';
		this.checkBox.name = descriptor.name;

		this.label = document.createElement('label');
		this.label.setAttribute('class', 'inline');
		this.label.setAttribute('for', 'albero-' + descriptor.name);
		this.label.innerHTML = descriptor.renderedTexts['label'];
	}

	CheckBox.prototype = {
		addTo: function(container) {
			container.appendChild(this.checkBox);
			container.appendChild(this.label);
		},

		getLabel: function() {
			return this.label;
		},

		getValue: function() {
			return this.checkBox.checked;
		},

		setValue: function(value) {
			this.checkBox.checked = value;
		}
	};

	return {
		TextField: TextField,
		TextArea: TextArea,
		ComboBox: ComboBox,
		RadioButtons: RadioButtons,
		CheckBox: CheckBox
	};
}();

nl.trivento.albero.elements.ElementFactories = {
	addDefaultElementFactories: function(albero) {
		albero.addElementFactory('text field', nl.trivento.albero.elements.TextField);
		albero.addElementFactory('text area', nl.trivento.albero.elements.TextArea);
		albero.addElementFactory('combo box', nl.trivento.albero.elements.ComboBox);
		albero.addElementFactory('radio buttons', nl.trivento.albero.elements.RadioButtons);
		albero.addElementFactory('check box', nl.trivento.albero.elements.CheckBox);
	}
};
