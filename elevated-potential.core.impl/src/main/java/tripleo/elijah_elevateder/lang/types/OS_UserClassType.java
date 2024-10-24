package tripleo.elijah_elevateder.lang.types;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;


import tripleo.elijah_elevateder.stages.deduce.*;
import tripleo.elijah_elevateder.stages.gen_fn.GenType;
import tripleo.elijah_fluffy.util.Mode;
import tripleo.elijah_fluffy.util.Operation;

import java.util.*;

public class OS_UserClassType extends __Abstract_OS_Type {
	private final ClassStatement _classStatement;

	public OS_UserClassType(final ClassStatement aClassStatement) {
		_classStatement = aClassStatement;
	}

	@Override
	protected boolean _isEqual(final @NotNull OS_Type aType) {
		return aType.getType() == Type.USER_CLASS && Objects.equals(_classStatement, ((OS_UserClassType) aType)._classStatement);
	}

	@Override
	public @NotNull String asString() {
		return "<OS_UserClassType %s>".formatted(_classStatement);
	}

	@Override
	public ClassStatement getClassOf() {
		return _classStatement;
	}

	@Override
	public OS_Element getElement() {
		return _classStatement;
	}

	@Override
	public @NotNull Type getType() {
		return Type.USER_CLASS;
	}

	@NotNull
	public ClassInvocation resolvedUserClass(final @NotNull GenType genType, final TypeName aGenericTypeName,
	                                         final @NotNull DeducePhase phase, final DeduceTypes2 deduceTypes2) {
		final ClassStatement best = _classStatement;
		@Nullable
		final String constructorName = null; // TODO what to do about this, nothing I guess

		@NotNull
		final List<TypeName> gp = best.getGenericPart();
		@Nullable
		ClassInvocation clsinv;
		if (genType.getCi() == null) {
			final Operation<ClassInvocation> oi = DeduceTypes2.ClassInvocationMake.withGenericPart(best,
			                                                                                       constructorName, (NormalTypeName) aGenericTypeName, deduceTypes2);
			assert oi.mode() == Mode.SUCCESS;
			clsinv = oi.success();
			if (clsinv == null)
				return null;
			clsinv = phase.registerClassInvocation(clsinv);
			genType.setCi(clsinv);
		} else
			clsinv = (ClassInvocation) genType.getCi();
		return clsinv;
	}

	@Override
	public String toString() {
		return asString();
	}
}
