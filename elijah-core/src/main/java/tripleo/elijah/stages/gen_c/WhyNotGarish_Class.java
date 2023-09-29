package tripleo.elijah.stages.gen_c;

import org.apache.commons.lang3.tuple.*;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.nextgen.reactive.Reactivable;
import tripleo.elijah.nextgen.reactive.ReactiveDimension;
import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.world.i.*;

import static tripleo.elijah.DebugFlags.MANUAL_DISABLED;

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

	private final EvaClass gc;
	private final GenerateC generateC;

	private final DeferredObject<GenerateResultEnv, Void, Void> fileGenPromise = new DeferredObject<>();

	private final GCFC gcfc = new GCFC();

	public WhyNotGarish_Class(final EvaClass aGc, final GenerateC aGenerateC) {
		gc = aGc;
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
		NotImplementedException.raise();

		if (!MANUAL_DISABLED) {
			gcfc.respondTo(this.generateC);
		}

		final LivingRepo world = generateC._ce().getCompilation().world();
		var gc1 = world.getClass(gc).getGarish();

		final @NotNull GenerateResultSink sink = aFileGen.resultSink();

		if (sink != null) {
			sink.addClass_1(gc1, aFileGen.gr(), generateC);
		} else {
			logProgress(9991, "sink failed");
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
