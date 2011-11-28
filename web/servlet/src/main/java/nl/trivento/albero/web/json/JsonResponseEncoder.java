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
/*
 * Copyright 2009 Levi Hoogenberg
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
package nl.trivento.albero.web.json;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import nl.trivento.albero.model.Context;
import nl.trivento.albero.model.ContextException;
import nl.trivento.albero.model.forms.Form;
import nl.trivento.albero.model.forms.FormElement;
import nl.trivento.albero.model.forms.FormElementType;
import nl.trivento.albero.utilities.Logger;
import nl.trivento.albero.web.CodecException;
import nl.trivento.albero.web.ResponseEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * A response encoder that converts the evaluated context and its form to JSON objects and sends those to the response
 * writer.
 *
 */
public class JsonResponseEncoder implements ResponseEncoder {
	/** The logger to use. */
	protected final Logger logger;

	/**
	 * Creates a JSON response encoder.
	 */
	public JsonResponseEncoder() {
		logger = Logger.get(getClass());
	}

	public void writeResponse(Context context, Form form, HttpServletResponse response) throws CodecException {
		writeResponse(encodeResponse(context, form), response);
	}

	/**
	 * Encodes the response that will be sent back to the client.
	 *
	 * @param context the new context
	 * @param form the form (may be {@code null})
	 * @return the encoded response
	 * @throws CodecException when the response can't be encoded
	 */
	protected JsonObject encodeResponse(Context context, Form form) throws CodecException {
		JsonObject responseObject = new JsonObject();

		responseObject.put("context", encodeContext(context));

		if (form != null) {
			responseObject.put("form", encodeForm(form));
		}

		return responseObject;
	}

	private JsonObject encodeContext(Context context) throws CodecException {
		JsonObject contextObject = new JsonObject();

		for (String variableType: context.getVariableTypes()) {
			JsonObject variables = new JsonObject();

			for (String variableName: context.getVariableNames(variableType)) {
				if (context.shouldBeEncoded(variableType, variableName)) {
					try {
						variables.put(variableName, encodeVariable(context.getVariable(variableType, variableName)));
					} catch (ContextException exception) {
						throw new CodecException(exception, "can't encode context variable");
					}
				}
			}

			contextObject.put(variableType, variables);
		}

		return contextObject;
	}

	private Object encodeVariable(Object variable) {
		Object variableObject;

		if (variable instanceof Map) {
			JsonObject object = new JsonObject();

			for (Map.Entry<?, ?> entry: ((Map<?, ?>) variable).entrySet()) {
				object.put(entry.getKey(), encodeVariable(entry.getValue()));
			}

			variableObject = object;
		} else if (variable instanceof List) {
			JsonArray array = new JsonArray();

			for (Object element: (List<?>) variable) {
				array.add(encodeVariable(element));
			}

			variableObject = array;
		} else {
			variableObject = variable;
		}

		return variableObject;
	}

	private JsonObject encodeForm(Form form) {
		JsonObject formObject = new JsonObject();

		String name = form.getName();

		if (name != null) {
			formObject.put("name", name);
		}

		encodeTexts(formObject, "texts", form.getTexts());

		List<Form> forms = form.getForms();

		if (!forms.isEmpty()) {
			JsonArray formsArray = new JsonArray();

			for (Form subform: forms) {
				formsArray.add(encodeForm(subform));
			}

			formObject.put("forms", formsArray);
		}

		List<FormElement> elements = form.getElements();

		if (!elements.isEmpty()) {
			JsonArray elementsArray = new JsonArray();

			for (FormElement element: elements) {
				elementsArray.add(encodeFormElement(element));
			}

			formObject.put("elements", elementsArray);
		}

		return formObject;
	}

	private JsonObject encodeFormElement(FormElement element) {
		JsonObject elementObject = new JsonObject();

		elementObject.put("name", element.getName());
		elementObject.put("type", encodeFormElementType(element.getType()));

		String renderingHint = element.getRenderingHint();

		if (renderingHint != null) {
			elementObject.put("renderingHint", renderingHint);
		}

		List<String> validationErrors = element.getValidationErrors();

		if (!validationErrors.isEmpty()) {
			JsonArray validationErrorsArray = new JsonArray();

			for (String validationError: validationErrors) {
				validationErrorsArray.add(validationError);
			}

			elementObject.put("validationErrors", validationErrorsArray);
		}

		List<?> options = element.getOptions();

		if (!options.isEmpty()) {
			JsonArray optionsArray = new JsonArray();

			for (Object option: options) {
				optionsArray.add(option);
			}

			elementObject.put("options", optionsArray);
		}

		encodeTexts(elementObject, "texts", element.getTexts());

		return elementObject;
	}

	private JsonObject encodeFormElementType(FormElementType elementType) {
		JsonObject elementTypeObject = new JsonObject();

		elementTypeObject.put("name", elementType.getName());
		elementTypeObject.put("list", elementType.isList());

		return elementTypeObject;
	}

	private void encodeTexts(JsonObject textContainerObject, String key, Map<String, String> texts) {
		if (!texts.isEmpty()) {
			JsonObject textsObject = new JsonObject();
			textsObject.putAll(texts);

			textContainerObject.put(key, textsObject);
		}
	}

	/**
	 * Writes a response back to the client.
	 *
	 * @param responseObject the response object to write
	 * @param response the HTTP response object
	 * @throws CodecException when the response can't be written
	 */
	protected void writeResponse(JsonObject responseObject, HttpServletResponse response) throws CodecException {
		logger.debug("writing response: ", responseObject.toJSONString());

		try {
			responseObject.writeJSONString(response.getWriter());
		} catch (IOException exception) {
			throw new CodecException(exception, "can't write response");
		}
	}

	/**
	 * A {@link JSONArray JSON array} that suppresses unchecked compiler warnings.
	 *
	 */
	protected static class JsonArray extends JSONArray {
		@Override
		@SuppressWarnings("unchecked")
		public boolean add(Object element) {
			return super.add(element);
		}

		private final static long serialVersionUID = 20091224L;
	}

	/**
	 * A {@link JSONObject JSON object} that suppresses unchecked compiler warnings.
	 *
	 */
	protected static class JsonObject extends JSONObject {
		@Override
		@SuppressWarnings("unchecked")
		public Object put(Object key, Object value) {
			return super.put(key, value);
		}

		@Override
		@SuppressWarnings("unchecked")
		public void putAll(Map map) {
			super.putAll(map);
		}

		private final static long serialVersionUID = 20091224L;
	}
}
