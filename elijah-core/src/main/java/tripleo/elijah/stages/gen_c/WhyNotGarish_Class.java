package tripleo.elijah.stages.gen_c;

import org.apache.commons.lang3.tuple.*;
import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.CompProgress;
import tripleo.elijah.nextgen.reactive.*;
import tripleo.elijah.stages.garish.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.gen_generic.pipeline_impl.*;
import tripleo.elijah.util.*;
import tripleo.elijah.world.i.*;

import static tripleo.elijah.DebugFlags.*;

public class WhyNotGarish_Class implements WhyNotGarish_Item {
	public class GCFC implements Reactivable {

		@Override
		public void respondTo(final ReactiveDimension aDimension) {
			if (aDimension instanceof GenerateC generateC) {
				fileGenPromise.then(fileGen -> {
					final LivingClass livingClass = generateC._ce().getCompilation().world().getClass(gc);

					livingClass.generateWith(fileGen.resultSink(), livingClass.getGarish(), fileGen.gr(), generateC);
				});
			}
		}
	}

	private static final boolean                                       __Oct_13       = false;
	private final        EvaClass                                      gc;
	private final        GenerateC                                     generateC;
	private final        DeferredObject<GenerateResultEnv, Void, Void> fileGenPromise = new DeferredObject<>();
	private final        GCFC                                          gcfc           = new GCFC();

	public WhyNotGarish_Class(final EvaClass aGc, final GenerateC aGenerateC) {
		gc        = aGc;
		generateC = aGenerateC;

		gc.reactive().add(gcfc);

		fileGenPromise.then(this::onFileGen);
	}

	public String getTypeNameString() {
		return GenerateC.GetTypeName.forGenClass(gc);
	}

	@Override
	public boolean hasFileGen() {
		return fileGenPromise.isResolved();
	}

	private void onFileGen(final @NotNull GenerateResultEnv aFileGen) {
		//assert false;
		NotImplementedException.raise();

		if (__Oct_13) {
			//final Compilation compilation = aFileGen.gmgm().gmr().mod().module().getContext().compilation();
			final Compilation compilation = generateC._ce().getCompilation();

			// FIXME 10/13 which result sink?
			final GenerateResultSink resultSink2    = generateC.resultSink;
			final GenerateResultSink resultSink     = aFileGen.resultSink();
			final GenerateResult     generateResult = aFileGen.gr();
			final LivingClass        livingClass    = compilation.world().getClass(gc);
			final GarishClass        garishClass    = livingClass.getGarish();

			//assert resultSink == resultSink;

			gc.generator().provide(resultSink, garishClass, generateResult, generateC);
		} else {
			if (!MANUAL_DISABLED) {
				gcfc.respondTo(this.generateC);
			}

			final GenerateResult              generateResult = aFileGen.gr();

			final Compilation                 compilation    = generateC._ce().getCompilation();
			final LivingRepo                  world          = compilation.world();
			final GarishClass                 garishClass    = world.getClass(gc).getGarish();

			final @NotNull GenerateResultSink sink           = aFileGen.resultSink();
			if (sink == null) {
				logProgress(9991, "sink failed");
				return;
			}

			sink.addClass_1(garishClass, generateResult, generateC);
		}
	}

	@Override
	public void provideFileGen(final GenerateResultEnv fg) {
		fileGenPromise.resolve(fg);
	}

	private void logProgress(int code, String message) {
		generateC._ce().logProgress(CompProgress.GenerateC, Pair.of(code, message));
	}
}
