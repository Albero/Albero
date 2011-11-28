package nl.profict.albero.utilities;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

@SuppressWarnings("all")
@Test
public class StringUtilitiesTest {
	public void stringsShouldBeJoinable() {
		String joint = StringUtilities.join("to", "get", "her");
		assert joint != null;
		assert joint.equals("together");
	}

	public void objectsShouldBeJoinable() {
		String joint = StringUtilities.join("area", ' ', 51);
		assert joint != null;
		assert joint.equals("area 51");
	}

	public void singleParameterShouldBeInterpolated() {
		Map<String, String> parameters = Collections.singletonMap("name", "you");

		String interpolatedText = StringUtilities.interpolate("hello, {name}", parameters);
		assert interpolatedText != null;
		assert interpolatedText.equals("hello, you");
	}

	public void multipleParametersShouldBeInterpolated() {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("greeting", "hello");
		parameters.put("name", "you");

		String interpolatedText = StringUtilities.interpolate("{greeting}, {name}", parameters);
		assert interpolatedText != null;
		assert interpolatedText.equals("hello, you");
	}

	public void unknownParametersShouldNotBeReplaced() {
		String interpolatedText = StringUtilities.interpolate("{nonexistent}", Collections.<String, Object>emptyMap());
		assert interpolatedText != null;
		assert interpolatedText.equals("{nonexistent}");
	}
}