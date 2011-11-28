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

class ContextCondition extends Condition {
	private String name

	private Closure test

	public ContextCondition(String name) {
		this.name = name

		test = {context -> (Boolean) context.getVariable(Context.INFORMATION, name)}
	}

	Boolean evaluate(Context context) {
		context.getVariableNames(Context.INFORMATION).contains(name) ? test(context) : null
	}

	def propertyMissing(String name) {
        this.name = "${this.name}.${name}"

		this
	}

	Condition lessThan(Number number) {
		test = {context -> ((Number) context.getVariable(Context.INFORMATION, name)) < number}

		this
	}

	Condition lessThanOrEqualTo(Number number) {
		test = {context -> ((Number) context.getVariable(Context.INFORMATION, name)) <= number}

		this
	}

	Condition greaterThan(Number number) {
		test = {context -> ((Number) context.getVariable(Context.INFORMATION, name)) > number}

		this
	}

	Condition greaterThanOrEqualTo(Number number) {
		test = {context -> ((Number) context.getVariable(Context.INFORMATION, name)) >= number}

		this
	}

	Condition is(Object value) {
		test = {context -> context.getVariable(Context.INFORMATION, name) == value}

		this
	}

	Condition before(Date date) {
		test = {context -> Date.parse('d-M-yyyy', context.getVariable(Context.INFORMATION, name)).before(date)}

		this
	}

	Condition after(Date date) {
		test = {context -> Date.parse('d-M-yyyy', context.getVariable(Context.INFORMATION, name)).after(date)}

		this
	}

	Condition contains(Closure valueTest) {
		test = {context -> context.getVariable(Context.INFORMATION, name).find(valueTest) != null}

		this
	}

	Condition contains(Object value) {
		contains({it == value})
	}
}
