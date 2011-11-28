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

import nl.trivento.albero.model.model.Model
import nl.trivento.albero.model.questions.QuestionNode
import nl.trivento.albero.utilities.Builder

class QuestionNodeBuilder extends NodeBuilder {
	private Builder questionBuilder

	public QuestionNodeBuilder(def questionBuilderFactory) {
		questionBuilder = (questionBuilderFactory instanceof Closure) ? questionBuilderFactory() : questionBuilderFactory.newInstance()

		delegate = questionBuilder
		doNotDelegate 'code'
		doNotDelegate 'group'
	}

	QuestionNode buildNode(String code, String group, Model model) {
		new QuestionNode(code, group, questionBuilder.build(model))
	}
}
