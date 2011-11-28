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
package nl.profict.albero.model.external;

import java.util.HashMap;
import java.util.Map;

import nl.profict.albero.model.AbstractNode;
import nl.profict.albero.model.Context;
import nl.profict.albero.model.EvaluationContext;
import nl.profict.albero.model.EvaluationException;
import nl.profict.albero.model.forms.Form;

/**
 * Fills the model with a value from an {@link ExternalValueProvider external value provider}.
 *
 */
public class ExternalValueNode extends AbstractNode {
	private String propertyPath;
	private String externalValueProviderName;

	private Map<String, Object> parameters;

	/**
	 * Creates an external value node.
	 *
	 * @param code the code of the node
	 * @param group the group of the node
	 * @param propertyPath the path of the model property where the value will be set
	 * @param externalValueProviderName the name of the external value provider
	 */
	public ExternalValueNode(String code, String group, String propertyPath, String externalValueProviderName) {
		super(code, group);

		this.propertyPath = propertyPath;
		this.externalValueProviderName = externalValueProviderName;

		parameters = new HashMap<String, Object>();
	}

	/**
	 * Adds a parameter that will be given to the external value provider.
	 *
	 * @param name the name of the parameter to add
	 * @param value the value of the parameter to add
	 */
	public void addParameter(String name, Object value) {
		parameters.put(name, value);
	}

	@Override
	public boolean isEvaluatable(Context context) {
		return super.isEvaluatable(context) && !valueAvailable(context);
	}

	private boolean valueAvailable(Context context) {
		return context.getVariableTypes().contains(Context.INFORMATION) &&
			context.getVariableNames(Context.INFORMATION).contains(propertyPath);
	}

	public Form evaluate(EvaluationContext evaluationContext, boolean validate) throws EvaluationException {
		evaluationContext.getContext().setVariable(Context.INFORMATION, propertyPath,
				evaluationContext.findExternalValueProvider(externalValueProviderName).provideValue(parameters));

		return null;
	}
}
