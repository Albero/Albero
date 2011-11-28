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
package nl.profict.albero.model.model;

import nl.profict.albero.AlberoException;

/**
 * Thrown by a {@link Model model} when it is used inappropriately.
 *
 */
public class ModelException extends AlberoException {
	/**
	 * Creates a model exception.
	 *
	 * @param messageParts the parts of the exception message
	 */
	public ModelException(Object... messageParts) {
		super(messageParts);
	}

	/**
	 * Creates a model exception.
	 *
	 * @param cause the cause of the exception
	 * @param messageParts the parts of the exception message
	 */
	public ModelException(Throwable cause, Object... messageParts) {
		super(cause, messageParts);
	}

	private final static long serialVersionUID = 20090930L;
}
