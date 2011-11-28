tests(tree: 'name') {
	'initial question should ask both first and last name' {
		expectations {
			form {
				form {
					text(type: 'message', value: 'What is your name?')

					element(name: 'firstName') {
						type('text')

						text(type: 'label', value: 'First name')
					}

					element(name: 'lastName') {
						type('text')

						text(type: 'label', value: 'Last name')
					}
				}
			}
		}
	}
}