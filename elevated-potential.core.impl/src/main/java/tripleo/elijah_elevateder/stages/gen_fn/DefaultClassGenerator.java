package tripleo.elijah_elevateder.stages.gen_fn;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_elevateder.factory.NonOpinionatedBuilder;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah_elevateder.stages.deduce.ClassInvocation;
import tripleo.elijah_elevateder.stages.deduce.DeducePhase;
import tripleo.elijah_elevateder.stages.deduce.FunctionInvocation;
import tripleo.elijah_elevateder.stages.deduce.NULL_DeduceTypes2;
import tripleo.elijah_elevateder.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah.work.*;

public class DefaultClassGenerator implements IClassGenerator {
	private final ICodeRegistrar    cr;
	private final DeducePhase       _g_deducePhase;
	private final @NotNull WorkList wl;

	public DefaultClassGenerator(DeducePhase aDeducePhase, NonOpinionatedBuilder nob) {
		// given
		_g_deducePhase = aDeducePhase;

		// transitive
		cr = _g_deducePhase.getCodeRegistrar();

		// creating
		wl = nob.createWorkList(this);
	}

	@Override
	public ICodeRegistrar getCodeRegistrar() {
		return cr;
	}

	@Override
	public DeducePhase.@NotNull GeneratedClasses getGeneratedClasses() {
		return _g_deducePhase.generatedClasses;
	}

	@Override
	public FunctionInvocation newFunctionInvocation(final FunctionDef fd, final ProcTableEntry pte,
			final @NotNull ClassInvocation ci) {
		final @NotNull FunctionInvocation fi = _g_deducePhase.newFunctionInvocation(fd, pte, ci);
		return fi;
	}

	@Override
	public @Nullable ClassInvocation registerClassInvocation(final @NotNull ClassStatement cs, final String className) {
		final ClassInvocation ci = _g_deducePhase.registerClassInvocation(cs, className, new NULL_DeduceTypes2());
		return ci;
	}

	@Override
	public void submitGenerateClass(final @NotNull ClassInvocation ci, final GenerateFunctions gf) {
		wl.addJob(new WlGenerateClass(gf, ci, _g_deducePhase.generatedClasses, cr));
	}

	@Override
	public void submitGenerateFunction(final @NotNull FunctionInvocation fi, final GenerateFunctions gf) {
		wl.addJob(new WlGenerateFunction(gf, fi, cr));
	}

	@Override
	public @NotNull WorkList wl() {
		return wl;
	}
}
