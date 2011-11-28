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
package nl.trivento.albero.model.model;

import java.util.Set;

import nl.trivento.albero.model.model.Property;
import nl.trivento.albero.model.model.SimplePropertyType;
import nl.trivento.albero.model.validation.PropertyValidator;
import nl.trivento.albero.model.validation.RequiredPropertyValidator;

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
