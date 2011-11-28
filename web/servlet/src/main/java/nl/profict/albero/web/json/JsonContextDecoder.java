package nl.profict.albero.web.json;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import nl.profict.albero.model.Context;
import nl.profict.albero.model.DefaultContext;
import nl.profict.albero.utilities.Logger;
import nl.profict.albero.web.CodecException;
import nl.profict.albero.web.ContextDecoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * A context decoder that parses the contents of the request parameter {@value #CONTEXT_PARAMETER_NAME} into a JSON
 * object and converts it to a context.
 *
 * @author levi_h
 */
public class JsonContextDecoder implements ContextDecoder {
	private JSONParser jsonParser;

	/** The logger to use. */
	protected final Logger logger;

	/**
	 * Creates a JSON context decoder.
	 */
	public JsonContextDecoder() {
		jsonParser = new JSONParser();

		logger = Logger.get(getClass());
	}

	public Context decodeContext(HttpServletRequest request) throws CodecException {
		String parameter = request.getParameter(CONTEXT_PARAMETER_NAME);

		if (parameter == null) {
			throw new CodecException("can't find a request parameter named '", CONTEXT_PARAMETER_NAME, "'");
		}

		Object object;

		try {
			logger.debug("parsing context parameter: ", parameter);

			object = jsonParser.parse(parameter);
		} catch (ParseException exception) {
			throw new CodecException(exception, "can't parse context");
		}

		if (object instanceof JSONObject) {
			Context context = new DefaultContext();

			JSONObject jsonObject = (JSONObject) object;

			for (Object type: jsonObject.keySet()) {
				Object jsonVariables = jsonObject.get(type);

				if (jsonVariables instanceof JSONObject) {
					Map<String, ?> variables = decodeVariables((JSONObject) jsonVariables);

					for (String name: variables.keySet()) {
						if (context.shouldBeDecoded((String) type, name)) {
							context.setVariable((String) type, name, variables.get(name));
						}
					}
				} else {
					throw new CodecException(type, " variables are not a JSONObject: ", jsonVariables);
				}
			}

			logger.debug("parsed context: ", context);

			return context;
		} else {
			throw new CodecException("context is not a JSONObject: %s", object);
		}
	}

	private Map<String, Object> decodeVariables(JSONObject jsonVariables) {
		Map<String, Object> variables = new HashMap<String, Object>();

		for (Object name: jsonVariables.keySet()) {
			Map<String, Object> partialVariables = variables;

			String[] nameParts = ((String) name).split("\\.");

			for (int i = 0; i < nameParts.length - 1; i++) {
				String namePart = nameParts[i];

				if (!partialVariables.containsKey(namePart)) {
					partialVariables.put(namePart, new HashMap<String, Object>());
				}

				@SuppressWarnings("unchecked")
				Map<String, Object> newPartialVariables = (Map<String, Object>) partialVariables.get(namePart);

				partialVariables = newPartialVariables;
			}

			partialVariables.put(nameParts[nameParts.length - 1], decodeVariable(jsonVariables.get(name)));
		}

		return variables;
	}

	private Object decodeVariable(Object jsonVariable) {
		Object variable;

		if (jsonVariable instanceof JSONObject) {
			variable = decodeVariables((JSONObject) jsonVariable);
		} else if (jsonVariable instanceof JSONArray) {
			List<Object> variables = new LinkedList<Object>();

			for (Object jsonElement: (JSONArray) jsonVariable) {
				variables.add(decodeVariable(jsonElement));
			}

			variable = variables;
		} else {
			variable = jsonVariable;
		}

		return variable;
	}

	/** The name of the request parameter in which the context is expected. */
	public static final String CONTEXT_PARAMETER_NAME = "context";
}