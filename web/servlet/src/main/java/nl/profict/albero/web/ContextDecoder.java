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
package nl.profict.albero.web;

import javax.servlet.http.HttpServletRequest;

import nl.profict.albero.model.Context;

/**
 * Decodes a context that's sent by the client.
 *
 * @author levi_h
 */
public interface ContextDecoder {
	/**
	 * Reads a context from a servlet request and parses it.
	 *
	 * @param request the request that contains the context
	 * @return the decoded context
	 * @throws CodecException when the context can't be decoded
	 */
	Context decodeContext(HttpServletRequest request) throws CodecException;
}
