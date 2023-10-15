package tripleo.elijah.stages.gen_c;

import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.notation.*;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.logging.*;

public class WhyNotGarish_Function extends WhyNotGarish_BaseFunction implements WhyNotGarish_Item {
	private final BaseEvaFunction gf;

	private final GenerateC                                     generateC;
	private final DeferredObject<GenerateResultEnv, Void, Void> fileGenPromise = new DeferredObject<>();

	public WhyNotGarish_Function(final BaseEvaFunction aGf, final GenerateC aGenerateC) {
		gf        = aGf;
		generateC = aGenerateC;

		fileGenPromise.then(this::onFileGen);
	}

	@Contract(pure = true)
	@Nullable
	public DeducedBaseEvaFunction deduced(final @NotNull BaseEvaFunction aEvaFunction) {
		final GM_GenerateModule generateModule = generateC.getFileGen().gmgm();
		final DeducePhase deducePhase = generateModule.gmr().env().pa().getCompilationEnclosure().getPipelineLogic().dp;

		final DeduceTypes2 dt2 = deducePhase._inj().new_DeduceTypes2(aEvaFunction.module(), deducePhase,
				ElLog.Verbosity.VERBOSE);
		dt2.deduceOneFunction((EvaFunction) aEvaFunction, deducePhase);

		return new DefaultDeducedBaseEvaFunction(aEvaFunction);
	}

	@Override
	public BaseEvaFunction getGf() {
		return gf;
	}

	@Override
	public boolean hasFileGen() {
		return fileGenPromise.isResolved();
	}

	public void onFileGen(final @NotNull GenerateResultEnv aFileGen) {
		if (gf.getFD() == null) {
			// FIXME why? when?
			throw new IllegalStateException("[WhyNotGarish_Function::onFileGen] gf.getFD() == null");
		}
		Generate_Code_For_Method gcfm = new Generate_Code_For_Method(generateC, generateC.LOG);
		gcfm.generateCodeForMethod(deduced(gf), aFileGen);
	}

	@Override
	public void provideFileGen(final GenerateResultEnv fg) {
		fileGenPromise.resolve(fg);
	}

	public void resolveFileGenPromise(final GenerateResultEnv aFileGen) {
		if (!fileGenPromise.isResolved()) {
			fileGenPromise.resolve(aFileGen);
		} else {
			System.out.println("twice for " + generateC);
		}
	}
}
