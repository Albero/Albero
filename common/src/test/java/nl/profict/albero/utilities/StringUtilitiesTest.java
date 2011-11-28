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
