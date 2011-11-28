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
package nl.profict.albero.model;

/**
 * Uses a context to get to a result.
 *
 */
public interface ResultProvider {
	/**
	 * Evaluates a context and adds a result (if applicable) to it.
	 *
	 * @param evaluationContext the evaluation context that contains the context to evaluate
	 * @throws EvaluationException when the context can't be evaluated
	 */
	void evaluate(EvaluationContext evaluationContext) throws EvaluationException;
}
