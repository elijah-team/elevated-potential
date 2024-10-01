/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevateder.lang.types;

import org.jetbrains.annotations.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.lang.i.*;

import java.text.*;

/**
 * Created 7/8/21 6:00 AM
 */
public class OS_GenericTypeNameType extends __Abstract_OS_Type {

	private final OS_TypeNameElement genericTypename;

	public OS_GenericTypeNameType(final OS_TypeNameElement aGenericTypename) {
		genericTypename = aGenericTypename;
	}

	@Override
	protected boolean _isEqual(final @NotNull OS_Type aType) {
		return aType.getType() == Type.GENERIC_TYPENAME
				&& genericTypename.equals(((OS_GenericTypeNameType) aType).genericTypename);
	}

	@Override
	public @NotNull String asString() {
		return MessageFormat.format("<OS_GenericTypeNameType {0}>", genericTypename);
	}

	@Override
	public OS_Element getElement() {
		return genericTypename;
	}

	public TypeName getRealTypeName() {
		return genericTypename.getTypeName();
	}

	@Override
	public @NotNull Type getType() {
		return Type.GENERIC_TYPENAME;
	}
}

//
//
//
