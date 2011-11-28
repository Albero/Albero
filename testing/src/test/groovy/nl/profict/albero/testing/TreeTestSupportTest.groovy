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