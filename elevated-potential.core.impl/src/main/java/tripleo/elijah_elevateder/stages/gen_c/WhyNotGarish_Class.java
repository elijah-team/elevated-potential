package tripleo.elijah_elevateder.stages.gen_c;

import org.apache.commons.lang3.tuple.Pair;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.CompProgress;
import tripleo.elijah.nextgen.reactive.Reactivable;
import tripleo.elijah.nextgen.reactive.ReactiveDimension;
import tripleo.elijah_fluffy.util.NotImplementedException;
import tripleo.elijah_elevateder.comp.i.Compilation;
import tripleo.elijah_elevateder.stages.garish.GarishClass;
import tripleo.elijah_elevateder.stages.garish.GarishClass__addClass_1;
import tripleo.elijah_elevateder.stages.gen_fn.EvaClass;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateResult;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah_elevateder.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah_elevateder.world.i.LivingClass;
import tripleo.elijah_elevateder.world.i.LivingRepo;

import static tripleo.elijah_elevateder.DebugFlags.MANUAL_DISABLED;

public class WhyNotGarish_Class implements WhyNotGarish_Item {
	private static final boolean   __Oct_13       = false;
	private final        EvaClass  gc;
	private final        GenerateC generateC;
	private final DeferredObject<GenerateResultEnv, Void, Void> fileGenPromise = new DeferredObject<>();
	@SuppressWarnings("TypeMayBeWeakened")
	private final GCFC                                          gcfc           = new GCFC();
	public WhyNotGarish_Class(final EvaClass aGc, final GenerateC aGenerateC) {
		gc        = aGc;
		generateC = aGenerateC;

		gc.reactive().add(gcfc);

		fileGenPromise.then(this::onFileGen);
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
			final LivingClass livingClass = compilation.world().getClass(gc);
			final GarishClass garishClass = (GarishClass) livingClass.getGarish();

			assert resultSink == resultSink2;

			gc.generator().provide(resultSink, garishClass, generateResult, generateC);
		} else {
			if (!MANUAL_DISABLED) {
				gcfc.respondTo(this.generateC);
			}

			final GenerateResult generateResult = aFileGen.gr();

			final Compilation compilation = generateC._ce().getCompilation();
			final LivingRepo  world       = compilation.world();
			final GarishClass garishClass = (GarishClass) world.getClass(gc).getGarish();

			final @NotNull GenerateResultSink sink = aFileGen.resultSink();
			if (sink == null) {
				logProgress(9991, "sink failed");
				return;
			}

			sink.add(new GarishClass__addClass_1(garishClass, generateResult, generateC));
		}
	}

	@SuppressWarnings("SameParameterValue")
	private void logProgress(int code, String message) {
		generateC._ce().logProgress(CompProgress.GenerateC, Pair.of(code, message));
	}

	public String getTypeNameString() {
		return GenerateC.GetTypeName.forGenClass(gc);
	}

	@Override
	public boolean hasFileGen() {
		return fileGenPromise.isResolved();
	}

	@Override
	public void provideFileGen(final GenerateResultEnv fg) {
		fileGenPromise.resolve(fg);
	}

	public class GCFC implements Reactivable {

		@Override
		public void respondTo(final ReactiveDimension aDimension) {
			if (aDimension instanceof GenerateC generateC2) {
				fileGenPromise.then(fileGen -> {
					final LivingClass livingClass = generateC2._ce().getCompilation().world().getClass(gc);

					livingClass.generateWith(fileGen.resultSink(), livingClass.getGarish(), fileGen.gr(), generateC2);
				});
			}
		}
	}
}
