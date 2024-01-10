package tripleo.elijah.stages.gen_c;

import org.apache.commons.lang3.tuple.Pair;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;

import tripleo.elijah.comp.i.CompProgress;

import tripleo.elijah.nextgen.reactive.Reactivable;
import tripleo.elijah.nextgen.reactive.ReactiveDimension;

import tripleo.elijah.stages.garish.GarishNamespace;
import tripleo.elijah.stages.garish.GarishNamespace__addClass_1;
import tripleo.elijah.stages.gen_fn.EvaNamespace;

import tripleo.elijah.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;

import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util2.DebugFlags;

import tripleo.elijah.world.i.LivingNamespace;
import tripleo.elijah.world.i.LivingRepo;

public class WhyNotGarish_Namespace implements WhyNotGarish_Item {
	private final EvaNamespace en;
	private final GenerateC    generateC;
	private final DeferredObject<GenerateResultEnv, Void, Void> fileGenPromise = new DeferredObject<>();
	private final GCFN gcfn = new GCFN();

	public WhyNotGarish_Namespace(final EvaNamespace aEn, final GenerateC aGenerateC) {
		en        = aEn;
		generateC = aGenerateC;

		en.reactive().add(gcfn);

		fileGenPromise.then(this::onFileGen);
	}

	private void onFileGen(final @NotNull GenerateResultEnv aFileGen) {
		NotImplementedException.raise();

		if (!DebugFlags.MANUAL_DISABLED) {
			gcfn.respondTo(this.generateC);
		}

		final LivingRepo                  world = generateC._ce().getCompilation().world();
		final GarishNamespace             gn    = world.getNamespace(en).getGarish();
		final @NotNull GenerateResultSink sink  = aFileGen.resultSink();

		if (sink != null) {
			new GarishNamespace__addClass_1(gn, aFileGen.gr(), generateC).action(sink);
		} else {
			logProgress(9993, "sink failed");
		}
	}

	private void logProgress(int code, String message) {
		generateC._ce().logProgress(CompProgress.GenerateC, Pair.of(code, message));
	}

	public String getTypeNameString() {
		return GenerateC.GetTypeName.forGenNamespace(en);
	}

	@Override
	public boolean hasFileGen() {
		return fileGenPromise.isResolved();
	}

	@Override
	public void provideFileGen(final GenerateResultEnv fg) {
		fileGenPromise.resolve(fg);
	}

	public class GCFN implements Reactivable {

		@Override
		public void respondTo(final ReactiveDimension aDimension) {
			if (aDimension instanceof GenerateC generateC) {
				fileGenPromise.then(fileGen -> {
					final LivingNamespace livingNamespace = generateC._ce().getCompilation().world().getNamespace(en);

					livingNamespace.getGarish().garish(generateC, fileGen.gr(), fileGen.resultSink());
				});
			}
		}
	}
}
