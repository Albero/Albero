package nl.profict.albero.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.profict.albero.AlberoException;
import nl.profict.albero.Engine;
import nl.profict.albero.configuration.Configuration;
import nl.profict.albero.configuration.DefaultConfiguration;
import nl.profict.albero.model.Context;
import nl.profict.albero.model.forms.Form;
import nl.profict.albero.utilities.Logger;
import nl.profict.albero.web.json.JsonContextDecoder;
import nl.profict.albero.web.json.JsonResponseEncoder;

/**
 * Uses the {@link Engine Albero engine} to evaluate a context. The decoding of the context to evaluate and the encoding
 * of the evaluated context and its resulting form are delegated to a {@link ContextDecoder context decoder} and a
 * {@link ResponseEncoder response encoder}, respectively.
 *
 * @author levi_h
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