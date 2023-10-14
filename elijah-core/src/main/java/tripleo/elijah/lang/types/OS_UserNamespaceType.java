package tripleo.elijah.lang.types;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.util.*;

import java.util.*;

public class OS_UserNamespaceType extends __Abstract_OS_Type implements OS_Type {
	private final NamespaceStatementImpl namespaceStatement;

	public OS_UserNamespaceType(final NamespaceStatementImpl aNamespaceStatement) {
		namespaceStatement = aNamespaceStatement;
	}

	@Override
	protected boolean _isEqual(final @NotNull OS_Type aType) {
		if (aType.getType() == Type.USER_NAMESPACE) {
			final OS_UserNamespaceType userNamespaceType = (OS_UserNamespaceType) aType;
			return Objects.equals(userNamespaceType.getNamespaceOf(), namespaceStatement);
		}
		return false;
	}

	@Override
	public String asString() {
		return "<OS_UserNamespaceType %s>".formatted(namespaceStatement);
	}

	public NamespaceStatement getNamespaceOf() {
		return namespaceStatement;
	}

	@Override
	public OS_Element getElement() {
		return namespaceStatement;
	}

	@Override
	public Type getType() {
		return Type.USER_NAMESPACE;
	}

	@Override
	public String toString() {
		return asString();
	}

/*
	// FIXME 10/13 what is the difference?
	@Override
	public boolean isEqual(final OS_Type aType) {
		return _isEqual(aType);
	}
*/

/*
	@Override
	public OS_Type resolve(final Context ctx) {
//		return null;
//		throw new UnintendedUseException();
		throw new NotImplementedException();
	}
*/
}
