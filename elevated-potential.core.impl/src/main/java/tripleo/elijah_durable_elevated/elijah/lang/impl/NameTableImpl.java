/**
 *
 */
package tripleo.elijah_durable_elevated.elijah.lang.impl;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;

import java.util.*;

/**
 * @author Tripleo
 *         <p>
 *         Created Mar 29, 2020 at 7:00:10 PM
 */
public class NameTableImpl implements tripleo.elijah.lang.i.NameTable {

	class TypedElement {
		OS_Element element;
		OS_Type type;

		public TypedElement(final OS_Element element2, final OS_Type dtype) {
			this.element = element2;
			this.type = dtype;
		}

		@Override
		public @NotNull String toString() {
			return "TypedElement{" + "element=" + element + ", type=" + type + '}';
		}
	}

	@NotNull
	Map<String, TypedElement> members = new HashMap<String, TypedElement>();

	@Override
	public void add(final OS_Element element, final String name, final OS_Type dtype) {
//		element.setType(dtype);
		members.put(name, new TypedElement(element, dtype));
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_2("[NameTable#add] " + members);
	}
}

//
//
//
