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
package nl.profict.albero.model.builders;

import nl.profict.albero.AlberoException;

/**
 * Thrown by a builder if it is used inappropriately.
 *
 */
public class BuilderException extends AlberoException {
	/**
	 * Creates a builder exception.
	 *
	 * @param messageParts the parts of the exception message
	 */
	public BuilderException(Object... messageParts) {
		super(messageParts);
	}

	/**
	 * Creates a builder exception.
	 *
	 * @param cause the cause of the exception
	 * @param messageParts the parts of the exception message
	 */
	public BuilderException(Throwable cause, Object... messageParts) {
		super(cause, messageParts);
	}

	private final static long serialVersionUID = 20090820L;
}
