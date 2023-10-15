package tripleo.elijah.stages.gen_c;

import org.apache.commons.lang3.tuple.*;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.DebugFlags;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.notation.*;
import tripleo.elijah.lang.i.IdentExpression;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah.stages.gen_generic.Old_GenerateResult;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.work.WorkList;
import tripleo.elijah.world.WorldGlobals;

import java.util.List;

public class WhyNotGarish_Constructor extends WhyNotGarish_BaseFunction implements WhyNotGarish_Item {
	private final EvaConstructor gf;
	private final GenerateC generateC;
	private final DeferredObject<GenerateResultEnv, Void, Void> fileGenPromise = new DeferredObject<>();
	private List<C2C_Result>                                    _c2c_results;

	public WhyNotGarish_Constructor(final EvaConstructor aGf, final GenerateC aGenerateC) {
		gf = aGf;
		generateC = aGenerateC;

		fileGenPromise.then(this::onFileGen);
	}

	@Override
	public EvaConstructor cheat() {
		return gf;
	}

	private void generateIdent(@NotNull IdentTableEntry identTableEntry, @NotNull GenerateResultEnv aFileGen) {
		assert identTableEntry.isResolved();

		final @NotNull EvaNode x = identTableEntry.resolvedType();
		final WorkList wl = aFileGen.wl();

		if (x instanceof final EvaClass evaClass) {
			generateC.generate_class(aFileGen, evaClass);
		} else if (x instanceof final EvaFunction evaFunction) {
			wl.addJob(new GenerateC.WlGenerateFunctionC(aFileGen, evaFunction, generateC));
		} else {
			generateC.LOG.err(x.toString());
			throw new NotImplementedException();
		}
	}

	@NotNull
	String getConstructorNameText() {
		final IdentExpression constructorName = gf.getFD().getNameNode();

		final String constructorNameText;
		if (constructorName == WorldGlobals.emptyConstructorName) {
			constructorNameText = "";
		} else {
			constructorNameText = constructorName.getText();
		}
		return constructorNameText;
	}

	@Override
	public BaseEvaFunction getGf() {
		return gf;
	}

	@Override
	public boolean hasFileGen() {
		return fileGenPromise.isResolved();
	}

	void onFileGen(final @NotNull GenerateResultEnv aFileGen) {
		final Generate_Code_For_Method gcfm = new Generate_Code_For_Method(generateC, generateC.elLog());

		var yf = generateC.a_lookup(gf);

		// TODO separate into method and method_header??
		final C2C_CodeForConstructor cfm = new C2C_CodeForConstructor(gcfm, aFileGen, yf);

		// cfm.calculate();
		final List<C2C_Result> rs = cfm.getResults();
		final GenerateResult gr = new Old_GenerateResult();
		final GCFC gcfc = new GCFC(rs, gf, gr); // TODO 08/12 preload this??

		_c2c_results = rs;

		gf.reactive().add(gcfc);

		if (!DebugFlags.MANUAL_DISABLED) {
			gcfc.respondTo(generateC);
		}

		// FIXME 06/17; 10/13 what's wrong with it?
		final GenerateResultSink sink = aFileGen.resultSink();
		if (sink != null) {
			sink.addFunction(gf, rs, generateC);
		} else {
			logProgress(9992, "sink failed");
		}
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

	@Override
	public void provideFileGen(final GenerateResultEnv fg) {
		fileGenPromise.resolve(fg);
	}

	public void resolveFileGenPromise(final GenerateResultEnv aFileGen) {
		fileGenPromise.resolve(aFileGen);
	}

	private void logProgress(int code, String message) {
		generateC._ce().logProgress(CompProgress.GenerateC, Pair.of(code, message));
	}

	public List<C2C_Result> getResults() {
		return _c2c_results;
	}

    public DeducedEvaConstructor deduced(final EvaConstructor gf) {

		// TODO/FIXME 10/15 cache results

		final GM_GenerateModule generateModule = generateC.getFileGen().gmgm();
		final DeducePhase deducePhase = generateModule.gmr().env().pa().getCompilationEnclosure().getPipelineLogic().dp;
		final DeduceTypes2 dt2 = deducePhase._inj().new_DeduceTypes2(gf.module(), deducePhase, ElLog.Verbosity.VERBOSE);

		dt2.deduceOneConstructor(gf, deducePhase);

		return new DefaultDeducedEvaConstructor(gf);
	}
}
