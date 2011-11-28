package nl.profict.albero.parsers.groovy.builders.nodes

import nl.profict.albero.model.ValuesNode
import nl.profict.albero.model.model.Model

class ValueContainer extends NodeBuilder {
	ValueContainer() {
		addBuilder('value', ValueBuilder)

		defaultProperty = 'property'
	}

	ValuesNode buildNode(String code, String group, Model model) {
		Map values = [:]

		boolean list = model.getProperty(property).isList()

		buildCollection('value', {value ->
			if (list) {
				if (!values.containsKey(property)) {
					values[property] = []
				}

				values[property] << value
			} else {
				values[property] = value
			}
		})

		new ValuesNode(code, group, values)
	}
}