/* Copyright 2011-2012 Profict Holding 
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package nl.trivento.albero.parsers.groovy.model

import nl.trivento.albero.model.Context

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
