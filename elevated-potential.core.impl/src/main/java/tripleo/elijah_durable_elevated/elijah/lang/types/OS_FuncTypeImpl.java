/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */

/**
 *
 */
package tripleo.elijah_durable_elevated.elijah.lang.types;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.types.GClosure_OS_FuncType_resolvedFunction0;
import tripleo.elijah.lang.types.OS_FuncType;
import tripleo.elijah.util.Mode;
import tripleo.elijah.util.Operation;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.*;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.GenType;

import java.text.MessageFormat;
import java.util.List;

public class OS_FuncTypeImpl extends __Abstract_OS_Type implements OS_FuncType {

	@Override
	public @NotNull boolean resolvedFunction(final GClosure_OS_FuncType_resolvedFunction0 cl0) {
		final GClosure_OS_FuncType_resolvedFunction cl = (GClosure_OS_FuncType_resolvedFunction) cl0;
		final ClassInvocation                       x  = resolvedFunction(cl.genType, cl.genericTypeName, cl.deduceTypes2, cl.phase);

		cl.setResult(x != null);

		return cl.result();
	}

	private final FunctionDef function_def;

	public OS_FuncTypeImpl(final FunctionDef functionDef) {
		this.function_def = functionDef;
	}

	@Override
	public boolean _isEqual(final @NotNull OS_Type aType) {
		return aType.getType() == Type.FUNCTION && function_def.equals(aType.getElement());
	}

	@Override
	public @NotNull String asString() {
		return MessageFormat.format("<OS_FuncType {0}>", function_def);
	}

	@Override
	public OS_Element getElement() {
		return function_def;
	}

	@Override
	public @NotNull Type getType() {
		return Type.FUNCTION;
	}

	public class GClosure_OS_FuncType_resolvedFunction implements GClosure_OS_FuncType_resolvedFunction0 {
		private final GenType      genType;
		private final TypeName     genericTypeName;
		private final DeduceTypes2 deduceTypes2;
		private final DeducePhase  phase;
		private       boolean      result;

		public GClosure_OS_FuncType_resolvedFunction(@NotNull GenType genType,
													 TypeName aGenericTypeName,
													 DeduceTypes2 deduceTypes2,
													 @NotNull DeducePhase phase) {
			this.genType = genType;
			genericTypeName = aGenericTypeName;
			this.deduceTypes2 = deduceTypes2;
			this.phase = phase;
		}

		public void setResult(final boolean aB) {
			this.result = aB;
		}

		@Override
		public boolean result() {
			return result;
		}
	}

	@NotNull
	public ClassInvocation resolvedFunction(final @NotNull GenType genType, final TypeName aGenericTypeName,
											final DeduceTypes2 deduceTypes2, final @NotNull DeducePhase phase) {
		// TODO what to do here?
		final OS_Element ele = function_def;
		final @Nullable ClassStatement best = (ClassStatement) ele.getParent();// genType.resolved.getClassOf();
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
		return String.format("<OS_FuncType %s>", function_def);
	}
}

//
//
//
