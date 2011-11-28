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

import java.util.Set;

/**
 * Contains all state that's built when completing a {@link Tree decision tree}.
 * <p>
 * A context contains a number of variables. A variable has a <i>type</i>. At the very least, the following types should
 * be supported:
 * <ul>
 * <li>{@value #ALBERO}
 * <li>{@value #INFORMATION}
 * <li>{@value #RESULTS}
 * </ul>
 *
 * @author levi_h
 */
public interface Context {
	/**
	 * Returns the names of all available variable types.
	 *
	 * @return a set that contains all variable types
	 */
	Set<String> getVariableTypes();

	/**
	 * Returns all variable names with a certain type.
	 *
	 * @param type the type to filter on
	 * @return a set that contains the names of all context variables with the given type
	 */
	Set<String> getVariableNames(String type);

	/**
	 * Returns the value of a variable.
	 *
	 * @param type the type of the variable to return
	 * @param name the name of the variable to return
	 * @return the value of the variable with the given name and type
	 * @throws ContextException when no variable with the given type and name exists
	 */
	Object getVariable(String type, String name) throws ContextException;

	/**
	 * Sets (or replaces) a variable.
	 *
	 * @param type the type of the variable to set
	 * @param name the name of the variable to set
	 * @param value the value to use
	 */
	void setVariable(String type, String name, Object value);

	/**
	 * Removes a variable.
	 *
	 * @param type the type of the variable to return
	 * @param name the name of the variable to remove
	 * @throws ContextException when no variable with the given type and name exists
	 */
	void removeVariable(String type, String name) throws ContextException;

	/**
	 * Determines whether a variable should be sent back to the caller.
	 *
	 * @param type the type of the variable to check
	 * @param name the name of the variable to check
	 * @return {@code true} if the variable with the given type and name should be encoded, {@code false} if it
	 *         shouldn't
	 */
	boolean shouldBeEncoded(String type, String name);

	/**
	 * Determines whether a variable should be processed when a caller sends it.
	 *
	 * @param type the type of the variable to check
	 * @param name the name of the variable to check
	 * @return {@code true} if the variable with the given type and name should be decoded, {@code false} if it
	 *         shouldn't
	 */
	boolean shouldBeDecoded(String type, String name);

	/** The type of control variables. */
	String ALBERO = "albero";

	/** The type of variables that contain all collected information. */
	String INFORMATION = "information";

	/** The type of variables that contain provided results. */
	String RESULTS = "results";
}
