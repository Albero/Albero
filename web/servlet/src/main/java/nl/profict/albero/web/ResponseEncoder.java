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