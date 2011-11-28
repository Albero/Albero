tree(code: 'voting') {
	dictionary {
		withRole('en') {
			translation(key: 'model.age', translation: 'Age')

			translation(key: 'validation.required', translation: '{property} is required.')
		}
	}

	model {
		age('number').required()
	}

	nodes {
		question(code: 'age', answers: 'age', labelText: 'What is your age?')
	}

	results {
		model {
			votingAllowed 'boolean'
		}

		rules {
			rule {
				when age.lessThan(18)
				set 'votingAllowed', false
			}

			rule {
				when age.greaterThanOrEqualTo(18)
				set 'votingAllowed', true
			}
		}
	}
}