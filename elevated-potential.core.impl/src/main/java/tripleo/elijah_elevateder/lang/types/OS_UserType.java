package tripleo.elijah_elevateder.lang.types;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;

import java.text.*;

public class OS_UserType extends __Abstract_OS_Type {
	private final TypeName typeName;

	public OS_UserType(final TypeName aTypeName) {
		typeName = aTypeName;
	}

	@Override
	protected boolean _isEqual(final @NotNull OS_Type aType) {
		return aType.getType() == Type.USER && typeName.equals(aType.getTypeName());
	}

	@Override
	public @NotNull String asString() {
		return MessageFormat.format("<OS_UserType {0}>", typeName);
	}

	@Override
	public @Nullable OS_Element getElement() {
		return null;
	}

	@Override
	public @NotNull Type getType() {
		return Type.USER;
	}

	@Override
	public TypeName getTypeName() {
		return typeName;
	}

	@Override
	public @Nullable OS_Type resolve(final @NotNull Context ctx) {
		assert ctx != null;

		final LookupResultList r = ctx.lookup(getTypeName().toString()); // TODO
		final OS_Element best = r.chooseBest(null);

		if (best == null)
			return null; // FIXME 07/03

		return ((ClassStatement) best).getOS_Type();
	}

	@Override
	public String toString() {
		return asString();
	}
}
