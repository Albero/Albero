tree(code: 'name') {
	model {
		firstName 'text'
		lastName 'text'
	}

	nodes {
		compoundQuestion(messageText: 'What is your name?') {
			question(labelText: 'First name', answers: 'firstName')
			question(labelText: 'Last name', answers: 'lastName')
		}
	}
}