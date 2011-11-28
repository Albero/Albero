tests(tree: 'voting') {
	'minor should not be allowed to vote' {
		information(variable: 'age', value: 16)

		node = 'age'

		expectations {
			result(variable: 'votingAllowed', value: false)
		}
	}

	'adult should be allowed to vote' {
		information(variable: 'age', value: 18)

		node = 'age'

		expectations.result(variable: 'votingAllowed', value: true)
	}
}