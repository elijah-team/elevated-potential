package tripleo.elijah.lang.types;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.*;

import java.text.*;

public class OS_BuiltinType extends __Abstract_OS_Type {
	private final BuiltInTypes _bit;

	public OS_BuiltinType(final BuiltInTypes aTypes) {
		_bit = (aTypes);
	}

	@Override
	protected boolean _isEqual(final @NotNull OS_Type aType) {
		return aType.getType() == Type.BUILT_IN && _bit.equals(aType.getBType());
	}

	@Override
	public @NotNull String asString() {
		return MessageFormat.format("<OS_BuiltinType {0}>", _bit);
	}

	@Override
	public BuiltInTypes getBType() {
		return _bit;
	}

	@Override
	public @Nullable OS_Element getElement() {
		return null;
	}

	@Override
	public @NotNull Type getType() {
		return Type.BUILT_IN;
	}
}
