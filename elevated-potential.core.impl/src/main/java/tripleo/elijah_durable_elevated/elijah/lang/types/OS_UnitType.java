package tripleo.elijah_durable_elevated.elijah.lang.types;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;

public class OS_UnitType extends __Abstract_OS_Type {

	@Override
	protected boolean _isEqual(final @NotNull OS_Type aType) {
		return aType.getType() == Type.UNIT_TYPE;
	}

	@Override
	public @NotNull String asString() {
		return "<OS_UnitType>";
	}

	@Override
	public @Nullable OS_Element getElement() {
		return null;
	}

	@Override
	public @NotNull Type getType() {
		return Type.UNIT_TYPE;
	}

	@Override
	public boolean isUnitType() {
		return true;
	}

	@Override
	public @NotNull String toString() {
		return "<UnitType>";
	}
}
