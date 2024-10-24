package tripleo.elijah_elevateder.stages.gen_c;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.i.CompProgress;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_fluffy.util.Eventual;
import tripleo.elijah_fluffy.util.NotImplementedException;
import tripleo.elijah.work.WorkList;
import tripleo.elijah_elevated_durable.lang_impl.LangGlobals;
import tripleo.elijah_elevateder.DebugFlags;
import tripleo.elijah_elevateder.comp.notation.GM_GenerateModule;
import tripleo.elijah_elevateder.stages.deduce.*;
import tripleo.elijah_elevateder.stages.garish.GarishConstructor__addFunction;
import tripleo.elijah_elevateder.stages.gen_fn.*;
import tripleo.elijah_elevateder.stages.gen_generic.*;
import tripleo.elijah_elevateder.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah_elevateder.stages.logging.ElLog_;

import java.util.List;

public class WhyNotGarish_Constructor extends WhyNotGarish_BaseFunction implements WhyNotGarish_Item {
	private final WhyNotGarish_DeclaringContext __declaringContext;
	private final EvaConstructor                gf;
	private final GenerateC                     generateC;
	private final Eventual<GenerateResultEnv>  fileGenPromise = new Eventual<>();
	private       DefaultDeducedEvaConstructor __deduced;
	private       List<C2C_Result>             _c2c_results;

	public WhyNotGarish_Constructor(final EvaConstructor aGf, final GenerateC aGenerateC) {
		gf        = aGf;
		generateC = aGenerateC;

		fileGenPromise.then(this::onFileGen);
		__declaringContext = new WhyNotGarish_DeclaringContext() {
			@Override
			public boolean isClassStatement() {
				return gf.getFD().getParent() instanceof ClassStatement;
			}

			@Override
			public boolean pointsToConstructor() {
				return getGf().getFD() instanceof ConstructorDef;
			}

			@Override
			public boolean pointsToConstructor2() {
				return getGf() instanceof EvaConstructor;
			}

			@Override
			public boolean isDefaultConstructor() {
				final String constructorName_ = cd().name();
				if (constructorName_.equals("<>"))
					return true;
				return false;
			}
		};
	}

	void onFileGen(final @NotNull GenerateResultEnv aFileGen) {
		final Generate_Code_For_Method gcfm = new Generate_Code_For_Method(generateC, generateC.elLog());

		var yf = generateC.a_lookup(gf);
		assert yf == this;

		// TODO separate into method and method_header??
		final C2C_CodeForConstructor cfm = new C2C_CodeForConstructor(gcfm, aFileGen, yf);

		// cfm.calculate();
		final List<C2C_Result> rs   = cfm.getResults();
		final GenerateResult   gr   = new Old_GenerateResult();
		final GCFC             gcfc = new GCFC(rs, gf, gr); // TODO 08/12 preload this??

		_c2c_results = rs;

		gf.reactive().add(gcfc);

		if (!DebugFlags.MANUAL_DISABLED) {
			gcfc.respondTo(generateC);
		}

		// FIXME 06/17; 10/13 what's wrong with it?
		final GenerateResultSink sink = aFileGen.resultSink();
		if (sink != null) {
			sink.add(new GarishConstructor__addFunction(gf, rs, generateC));
		} else {
			logProgress(9992, "sink failed");
		}
	}

	private void logProgress(int code, String message) {
		generateC._ce().logProgress(CompProgress.GenerateC, Pair.of(code, message));
	}

	@Override
	public EvaConstructor cheat() {
		return gf;
	}

	@Override
	public BaseEvaFunction getGf() {
		return gf;
	}

	@Override
	public void provideFileGen(final GenerateResultEnv fg) {
		fileGenPromise.resolve(fg);
	}

	@Override
	public WhyNotGarish_DeclaringContext declaringContext() {
		return __declaringContext;
	}

	@NotNull
	String getConstructorNameText() {
		final IdentExpression constructorName = gf.getFD().getNameNode();

		final String constructorNameText;
		if (constructorName == LangGlobals.emptyConstructorName) {
			constructorNameText = "";
		} else {
			constructorNameText = constructorName.getText();
		}
		return constructorNameText;
	}

	@Override
	public boolean hasFileGen() {
		return fileGenPromise.isResolved();
	}

	public void postGenerateCodeForConstructor(final WorkList aWl, final GenerateResultEnv aFileGen) {
		for (IdentTableEntry identTableEntry : gf.idte_list) {
			// IdentTableEntry.;

			identTableEntry.reactive().addResolveListener((IdentTableEntry x) -> {
				generateIdent(x, aFileGen);
			});

			if (identTableEntry.isResolved()) {
				generateIdent(identTableEntry, aFileGen);
			}
		}
		for (ProcTableEntry pte : gf.prte_list) {
//                      ClassInvocation ci = pte.getClassInvocation();
			FunctionInvocation fi = pte.getFunctionInvocation();
			if (fi == null) {
				// TODO constructor
				int y = 2;
			} else {
				BaseEvaFunction gf = fi.getEva();
				if (gf != null) {
					aWl.addJob(new GenerateC.WlGenerateFunctionC(aFileGen, gf, generateC));
				}
			}
		}
	}

	private void generateIdent(@NotNull IdentTableEntry identTableEntry, @NotNull GenerateResultEnv aFileGen) {
		assert identTableEntry.isResolved();

		final @NotNull EvaNode x  = identTableEntry.resolvedType();
		final WorkList         wl = aFileGen.wl();

		if (x instanceof final EvaClass evaClass) {
			generateC.generate_class(aFileGen, evaClass);
		} else if (x instanceof final EvaFunction evaFunction) {
			wl.addJob(new GenerateC.WlGenerateFunctionC(aFileGen, evaFunction, generateC));
		} else {
			generateC.LOG.err(x.toString());
			throw new NotImplementedException();
		}
	}

	public void resolveFileGenPromise(final GenerateResultEnv aFileGen) {
		fileGenPromise.resolve(aFileGen);
	}

	public List<C2C_Result> getResults() {
		return _c2c_results;
	}

	public DeducedEvaConstructor deduced() {
		if (__deduced == null) {
			final GM_GenerateModule generateModule = generateC.getFileGen().gmgm();
			final DeducePhase       deducePhase    = generateModule.gmr().env().pa().getCompilationEnclosure().getPipelineLogic().dp;

			// TODO 10/16 cached: tho this may not matter
			final DeduceTypes2      dt2            = deducePhase._inj().new_DeduceTypes2(gf.module(), deducePhase, ElLog_.Verbosity.VERBOSE);

			dt2.deduceOneConstructor(gf, deducePhase);

			__deduced = new DefaultDeducedEvaConstructor(gf);
		}

		return __deduced;
	}

	public @Nullable ConstructorDef cd() {
		return gf.cd;
	}
}
