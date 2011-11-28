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