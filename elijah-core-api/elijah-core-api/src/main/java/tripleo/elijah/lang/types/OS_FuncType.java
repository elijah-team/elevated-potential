package tripleo.elijah.lang.types;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;

public interface OS_FuncType extends OS_Type {
	boolean _isEqual(@NotNull OS_Type aType);

	@NotNull String asString();

	@Override
	OS_Element getElement();

	@Override
	@NotNull Type getType();

	@NotNull boolean resolvedFunction(GClosure_OS_FuncType_resolvedFunction0 cl);

	@Override
	String toString();
}
