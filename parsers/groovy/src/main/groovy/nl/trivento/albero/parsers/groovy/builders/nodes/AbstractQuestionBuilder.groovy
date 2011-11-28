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
package nl.trivento.albero.parsers.groovy.builders.nodes

import nl.trivento.albero.model.questions.AbstractQuestion
import nl.trivento.albero.utilities.Builder

abstract class AbstractQuestionBuilder extends Builder {
	private Map texts

	AbstractQuestionBuilder() {
		texts = [:]
	}

	@Override
	void setProperty(String name, value) {
		def textMatcher = (name =~ /(.*)Text$/)

		if (textMatcher) {
			texts[textMatcher[0][1]] = value
		} else {
			super.setProperty(name, value)
		}
	}

	protected void addTexts(AbstractQuestion question) {
		texts.each {String type, String text ->
			question.addText(type, text)
		}
	}
}
