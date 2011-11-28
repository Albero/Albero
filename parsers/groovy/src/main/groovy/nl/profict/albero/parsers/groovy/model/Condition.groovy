package nl.profict.albero.parsers.groovy.model

import nl.profict.albero.model.Context

abstract class Condition {
	abstract Boolean evaluate(Context context)

	Condition inverted() {
		def condition = this

		[evaluate: {map ->
			truthTable([
			[null]: null,
			[Boolean.FALSE]: Boolean.TRUE,
			[Boolean.TRUE]: Boolean.FALSE
			], [condition.evaluate(map)])
		}] as Condition
	}

	Condition or(Condition otherCondition) {
		def condition = this

		[evaluate: {map ->
			truthTable([
				[null, null]: null,
				[null, Boolean.FALSE]: null,
				[null, Boolean.TRUE]: Boolean.TRUE,
				[Boolean.FALSE, Boolean.FALSE]: Boolean.FALSE,
				[Boolean.FALSE, Boolean.TRUE]: Boolean.TRUE,
				[Boolean.TRUE, Boolean.TRUE]: Boolean.TRUE
			], [condition.evaluate(map), otherCondition.evaluate(map)])
		}] as Condition
	}

	Condition and(Condition otherCondition) {
		def condition = this

		[evaluate: {map ->
			truthTable([
				[null, null]: null,
				[null, Boolean.FALSE]: null,
				[null, Boolean.TRUE]: null,
				[Boolean.FALSE, Boolean.FALSE]: Boolean.FALSE,
				[Boolean.FALSE, Boolean.TRUE]: Boolean.FALSE,
				[Boolean.TRUE, Boolean.TRUE]: Boolean.TRUE
			], [condition.evaluate(map), otherCondition.evaluate(map)])
		}] as Condition
	}

	protected Boolean truthTable(Map table, List key) {
		table.containsKey(key) ? table[key] : table[key.reverse()]
	}
}