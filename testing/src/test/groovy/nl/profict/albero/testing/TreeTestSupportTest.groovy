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
package nl.profict.albero.testing

import org.testng.annotations.Test

class TreeTestSupportTest extends TreeTestSupport {
	@Test
	void scriptWithValidResultExpectationsShouldSucceed() {
		checkScript('scripts/valid_result_expectations.groovy')
	}

	@Test(expectedExceptions = AssertionError)
	void scriptWithInvalidResultExpectationsShouldCauseException() {
		checkScript('scripts/invalid_result_expectations.groovy')
	}

	@Test
	void scriptWithValidFormExpectationsShouldSucceed() {
		checkScript('scripts/valid_form_expectations.groovy')
	}

	@Test
	void scriptWithValidSubformExpectationsShouldSucceed() {
		checkScript('scripts/valid_subform_expectations.groovy')
	}

	@Test(expectedExceptions = AssertionError)
	void scriptWithInvalidFormExpectationsShouldCauseException() {
		checkScript('scripts/invalid_form_expectations.groovy')
	}
}
