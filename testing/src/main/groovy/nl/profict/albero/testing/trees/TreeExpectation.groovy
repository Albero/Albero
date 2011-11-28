package nl.profict.albero.testing.trees

import nl.profict.albero.model.Context
import nl.profict.albero.model.forms.Form

interface TreeExpectation {
	List check(Context context, Form form)	
}