package tripleo.elijah_elevateder.pre_world;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_elevateder.entrypoints.ArbitraryFunctionEntryPoint;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah_elevateder.stages.deduce.ClassInvocation;
import tripleo.elijah_elevateder.stages.deduce.FunctionInvocation;
import tripleo.elijah_elevateder.stages.gen_fn.GenerateFunctions;
import tripleo.elijah_elevateder.stages.gen_fn.IClassGenerator;
import tripleo.elijah_elevateder.stages.inter.ModuleThing;
import tripleo.small.ES_Symbol;

import java.util.Objects;

import static tripleo.elijah_fluffy.util.Helpers.List_of;

public class Mirror_ArbitraryFunctionEntryPoint implements Mirror_EntryPoint {
	private final ArbitraryFunctionEntryPoint afep;
	private final ModuleThing mt;
	private final GenerateFunctions gf;

	public Mirror_ArbitraryFunctionEntryPoint(final ArbitraryFunctionEntryPoint aAfep, final @NotNull ModuleThing aMt,
			final GenerateFunctions aGenerateFunctions) {
		afep = aAfep;
		mt = aMt;
		gf = aGenerateFunctions;
	}

	@Override
	public void generate(final @NotNull IClassGenerator dcg) {
		final ClassStatement cs = (ClassStatement) afep.getParent();
		final FunctionDef f = afep.getFunction();
		@Nullable
		final ClassInvocation ci = dcg.registerClassInvocation(cs, null);

		dcg.submitGenerateClass(Objects.requireNonNull(ci), gf);

		final @NotNull FunctionInvocation fi = dcg.newFunctionInvocation(f, null, ci);
		// fi.setPhase(deducePhase);
		dcg.submitGenerateFunction(fi, gf);

		mt.describe(new ModuleThing.GeneralDescription(new ES_Symbol("ArbitraryFunctionEntryPoint"), List_of(ci, fi)));
	}
}
