tests(tree: 'voting') {
	'minor should be allowed to vote' {
		information(variable: 'age', value: 16)

		node = 'age'

		expectations.result(variable: 'votingAllowed', value: true)
	}
}