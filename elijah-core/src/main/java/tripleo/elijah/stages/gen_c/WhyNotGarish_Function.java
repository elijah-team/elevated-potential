package tripleo.elijah.stages.gen_c;

import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.notation.*;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.logging.*;

public class WhyNotGarish_Function extends WhyNotGarish_BaseFunction implements WhyNotGarish_Item {
	private final BaseEvaFunction gf;
	private final WhyNotGarish_DeclaringContext __declaringContext;

	private final GenerateC                                     generateC;
	private final DeferredObject<GenerateResultEnv, Void, Void> fileGenPromise = new DeferredObject<>();
	private DefaultDeducedBaseEvaFunction __deduced;

	public WhyNotGarish_Function(final BaseEvaFunction aGf, final GenerateC aGenerateC) {
		gf        = aGf;
		generateC = aGenerateC;

		fileGenPromise.then(this::onFileGen);
		__declaringContext = new WhyNotGarish_DeclaringContext() {
			@Override
			public boolean isClassStatement() {
				return gf.getFD().getParent() instanceof ClassStatement;
			}
		};
	}

	@Contract(pure = true)
	private @Nullable DeducedBaseEvaFunction deduced(final @NotNull BaseEvaFunction aEvaFunction) {
		if (__deduced == null) {
			final GM_GenerateModule generateModule = generateC.getFileGen().gmgm();
			final DeducePhase       deducePhase    = generateModule.gmr().env().pa().getCompilationEnclosure().getPipelineLogic().dp;

			// TODO 10/16 cached: tho this may not matter
			final DeduceTypes2 dt2 = deducePhase._inj().new_DeduceTypes2(aEvaFunction.module(), deducePhase,
																		 ElLog.Verbosity.VERBOSE);

			dt2.deduceOneFunction((EvaFunction) aEvaFunction, deducePhase);

			__deduced = new DefaultDeducedBaseEvaFunction(aEvaFunction);
		}

		return __deduced;
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

	@Override
	public WhyNotGarish_DeclaringContext declaringContext() {
		return __declaringContext;
	}

	public void resolveFileGenPromise(final GenerateResultEnv aFileGen) {
		if (!fileGenPromise.isResolved()) {
			fileGenPromise.resolve(aFileGen);
		} else {
			System.out.println("twice for " + generateC);
		}
	}
}
