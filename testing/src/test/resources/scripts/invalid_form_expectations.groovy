tests(tree: 'voting') {
	'initial question should ask about age' {
		expectations {
			form {
				element(name: 'age') {
					type('number', list: false)

					text(type: 'label', value: "What's your age?")
				}
			}
		}
	}
}