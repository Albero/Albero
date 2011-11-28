tests(tree: 'voting') {
	'initial question should ask about age' {
		expectations {
			form {
				element(name: 'age') {
					type('number', list: false)

					text(type: 'label', value: 'What is your age?')
				}
			}
		}
	}

	'age should be required' {
		information(variable: 'age', value: null)

		node = 'age'

		expectations {
			form {
				element(name: 'age') {
					validationError 'Age is required.'
				}
			}
		}
	}
}