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
package nl.trivento.albero.configuration;

import nl.trivento.albero.AlberoException;

/**
 * Thrown by a {@link Configuration configuration} when something goes wrong during initialisation.
 *
 */
public class ConfigurationException extends AlberoException {
	/**
	 * Creates a configuration exception.
	 *
	 * @param messageParts the parts of the exception message
	 */
	public ConfigurationException(Object... messageParts) {
		super(messageParts);
	}

	/**
	 * Creates a configuration exception.
	 *
	 * @param cause the cause of the exception
	 * @param messageParts the parts of the exception message
	 */
	public ConfigurationException(Throwable cause, Object... messageParts) {
		super(cause, messageParts);
	}

	private final static long serialVersionUID = 20090831L;
}
