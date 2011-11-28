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

import javax.servlet.http.HttpServletResponse;

import nl.profict.albero.model.Context;
import nl.profict.albero.model.forms.Form;

/**
 * Encodes an evaluated context and the form that the evaluation produced and sends it back to the client.
 *
 * @author levi_h
 */
public interface ResponseEncoder {
	/**
	 * Encodes a context and a form and writes them to the servlet response.
	 *
	 * @param context the context to encode
	 * @param form the form to encode (may be {@code null})
	 * @param response the response to write the encoded
	 * @throws CodecException when the context or the form can't be encoded or when something goes wrong while writing
	 *                        to the response
	 */
	void writeResponse(Context context, Form form, HttpServletResponse response) throws CodecException;
}
