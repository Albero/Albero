package nl.profict.albero.model.model;

import java.util.Set;

import nl.profict.albero.model.validation.PropertyValidator;
import nl.profict.albero.model.validation.RequiredPropertyValidator;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SuppressWarnings("all")
@Test
public class PropertyTest {
	private Property property;

	@BeforeMethod
	public void createProperty() {
		property = new Property("name", new SimplePropertyType("text"));
	}

	public void newPropertyShouldNotHaveAnyValidators() {
		Set<PropertyValidator> validators = property.getValidators();
		assert validators != null;
		assert validators.isEmpty();
	}

	public void addedValidatorShouldBeAvailable() {
		PropertyValidator validator = new RequiredPropertyValidator();

		property.addValidator(validator);

		Set<PropertyValidator> validators = property.getValidators();
		assert validators != null;
		assert validators.size() == 1;
		assert validators.contains(validator);
	}
}