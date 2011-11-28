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
package nl.trivento.albero.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.trivento.albero.AlberoException;
import nl.trivento.albero.Engine;
import nl.trivento.albero.configuration.Configuration;
import nl.trivento.albero.configuration.DefaultConfiguration;
import nl.trivento.albero.model.Context;
import nl.trivento.albero.model.forms.Form;
import nl.trivento.albero.utilities.Logger;
import nl.trivento.albero.web.json.JsonContextDecoder;
import nl.trivento.albero.web.json.JsonResponseEncoder;

/**
 * Uses the {@link Engine Albero engine} to evaluate a context. The decoding of the context to evaluate and the encoding
 * of the evaluated context and its resulting form are delegated to a {@link ContextDecoder context decoder} and a
 * {@link ResponseEncoder response encoder}, respectively.
 *
 */
public class AlberoServlet extends HttpServlet {
	private Configuration configuration;
	private Engine engine;

	private ContextDecoder contextDecoder;
	private ResponseEncoder responseEncoder;

	private final Logger logger;

	/**
	 * Creates an Albero servlet.
	 */
	public AlberoServlet() {
		logger = Logger.get(getClass());
	}

	@Override
	public void init() throws ServletException {
		try {
			logger.debug("creating engine");

			configuration = new DefaultConfiguration(getConfigurationParameters());
			engine = new Engine(configuration);

			// TODO configurable
			contextDecoder = new JsonContextDecoder();
			responseEncoder = new JsonResponseEncoder();
		} catch (AlberoException exception) {
			throw new ServletException("can't create engine", exception);
		}
	}

	private Map<String, String> getConfigurationParameters() {
		Map<String, String> configurationParameters = new HashMap<String, String>();

		Enumeration<?> initParameterNames = getInitParameterNames();

		while (initParameterNames.hasMoreElements()) {
			String name = (String) initParameterNames.nextElement();

			configurationParameters.put(name, getInitParameter(name));
		}

		return configurationParameters;
	}

	@Override
	public void destroy() {
		configuration.close();
	}

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException {
		logger.debug("processing request");

		Context context = decodeContext(request);
		Form form = evaluate(context);
		writeResponse(context, form, response);

		logger.debug("finished processing request");
	}

	private Context decodeContext(HttpServletRequest request) throws ServletException {
		try {
			return contextDecoder.decodeContext(request);
		} catch (CodecException exception) {
			throw new ServletException("can't decode context", exception);
		}
	}

	/**
	 * Evaluates a context.
	 *
	 * @param context the context to evaluate
	 * @return the form that the user needs to fill in (can be {@code null})
	 * @throws ServletException when the context can't be evaluated
	 */
	protected Form evaluate(Context context) throws ServletException {
		try {
			logger.debug("evaluating context");

			return engine.evaluate(context);
		} catch (AlberoException exception) {
			throw new ServletException("can't evaluate context", exception);
		}
	}

	private void writeResponse(Context context, Form form, HttpServletResponse response) throws ServletException {
		response.setCharacterEncoding("UTF-8");

		try {
			responseEncoder.writeResponse(context, form, response);
		} catch (CodecException exception) {
			throw new ServletException("can't encode response", exception);
		}
	}

	private final static long serialVersionUID = 20110914L;
}
