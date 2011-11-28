package nl.profict.albero.model.model;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SuppressWarnings("all")
@Test
public class DefaultModelTest {
	private Model model;

	@BeforeMethod
	public void createModel() {
		DefaultModel model = new DefaultModel();
		model.addProperty(new Property("name", new SimplePropertyType("text")));

		CompoundPropertyType locationType = new CompoundPropertyType("location");
		locationType.addProperty(new Property("city", new SimplePropertyType("text")));
		locationType.addProperty(new Property("country", new SimplePropertyType("text")));

		model.addProperty(new Property("location", locationType));

		this.model = model;
	}

	public void simplePropertyShouldBeAvailable() {
		assert model.hasProperty("name");
	}

	public void simplePropertyShouldBeObtainable() {
		Property property = model.getProperty("name");
		assert property != null;

		String name = property.getName();
		assert name.equals("name");

		PropertyType type = property.getType();
		assert type != null;

		String typeName = type.getName();
		assert typeName.equals("text");

		List<Property> subproperties = type.getSubproperties();
		assert subproperties != null;
		assert subproperties.isEmpty();
	}

	public void compoundPropertyShouldBeAvailable() {
		assert model.hasProperty("location");
	}

	public void compoundPropertyShouldBeObtainable() {
		Property property = model.getProperty("location");
		assert property != null;

		String name = property.getName();
		assert name.equals("location");

		PropertyType type = property.getType();
		assert type != null;

		String typeName = type.getName();
		assert typeName.equals("location");

		List<Property> subproperties = type.getSubproperties();
		assert subproperties != null;
		assert subproperties.size() == 2;
	}

	public void nestedPropertyShouldBeAvailable() {
		assert model.hasProperty("location.country");
	}

	public void nestedPropertyShouldBeObtainable() {
		Property property = model.getProperty("location.country");
		assert property != null;

		String name = property.getName();
		assert name.equals("country");

		PropertyType type = property.getType();
		assert type != null;

		String typeName = type.getName();
		assert typeName.equals("text");

		List<Property> subproperties = type.getSubproperties();
		assert subproperties != null;
		assert subproperties.isEmpty();
	}
}