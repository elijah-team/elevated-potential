package tripleo.elijah_elevated_durable.pipelines.write;

import com.google.common.base.*;
import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;


import tripleo.elijah_elevated.comp.backbone.*;
import tripleo.elijah_elevateder.comp.i.extra.IPipelineAccess;
import tripleo.elijah_elevateder.comp.notation.*;
import tripleo.elijah_elevateder.nextgen.output.NG_OutputFunction;
import tripleo.elijah_elevateder.stages.gen_c.C2C_Result;
import tripleo.elijah_elevateder.stages.gen_c.GenerateC;
import tripleo.elijah_elevateder.stages.gen_fn.*;
import tripleo.elijah_elevateder.stages.gen_generic.*;
import tripleo.elijah_elevateder.stages.gen_generic.pipeline_impl.DefaultGenerateResultSink;
import tripleo.elijah_elevateder.stages.logging.ElLog_;
import tripleo.elijah_elevateder.work.EDL_WorkList;
import tripleo.elijah_elevateder.work.EDL_WorkManager;

import java.util.*;

import static tripleo.elijah.util.Helpers.*;

class AmazingFunction implements Amazing {
	private final NG_OutputFunction of;
	private final BaseEvaFunction   f;
	private final OS_Module         mod;
	private final WPIS_GenerateOutputs.OutputItems itms;
	private final GenerateResult                   result;
	private final IPipelineAccess                  pa;

	public AmazingFunction(final @NotNull BaseEvaFunction aBaseEvaFunction,
						   final @NotNull WPIS_GenerateOutputs.OutputItems aOutputItems,
						   final @NotNull GenerateResult aGenerateResult,
						   final @NotNull IPipelineAccess aPa) {
		// given
		f      = aBaseEvaFunction;
		mod    = aBaseEvaFunction.module();
		itms   = aOutputItems;
		pa     = aPa;
		result = aGenerateResult;

		// created
		of = new NG_OutputFunction();
	}

	public OS_Module mod() {
		return mod;
	}

	void waitGenC(final GenerateC ggc) {
		// TODO latch
		pa.getAccessBus().subscribePipelineLogic(aPipelineLogic -> {
			// FIXME check arguments --> this doesn't seem like it will give the desired
			// results
			DefaultGenerateResultSink generateResultSink = new DefaultGenerateResultSink(pa);

			GenerateResult       gr            = result; // new Old_GenerateResult();
			CompilationEnclosure ce            = pa.getCompilationEnclosure();

			var env = new GN_GenerateNodesIntoSinkEnv(List_of(),
			                                          generateResultSink,
			                                          ElLog_.Verbosity.VERBOSE,
			                                          gr,
			                                          ce);

			var world = ce.getCompilation().world();
			var wm    = world.findModule(mod);

			var generateModuleRequest = new GM_GenerateModuleRequest(new GN_GenerateNodesIntoSink(env), wm, env);
			var generateModule        = new GM_GenerateModule(generateModuleRequest);

			var fileGen = new GenerateResultEnv(new MyGenerateResultSink(of),
			                                    result,
			                                    new EDL_WorkManager(),
			                                    new EDL_WorkList(),
			                                    generateModule);

//			var generateModuleResult = generateModule.getModuleResult(fileGen.wm(), fileGen.resultSink());

			ProgressiveGenerateFiles
					.of(this)
					.onFileGen(ggc1 -> {
						if (f instanceof EvaFunction ff) {
							ggc1.generateCodeForMethod(fileGen, ff);
						} else if (f instanceof EvaConstructor fc) {
							ggc1.generateCodeForConstructor_1(fc, fileGen);
						}
					})
					.with(ggc); // NOTE 11/26 too involved to back out. this is cheating, tho.


			itms.addItem(of);
		});
	}

	interface ProgressiveGenerateFiles_Amazing_ {
		void with(GenerateC aGgc);
	}

	static class ProgressiveGenerateFiles_AmazingFunction implements ProgressiveGenerateFiles, ProgressiveGenerateFiles_Amazing_ {
		private final AmazingFunction   amazingFunction;
		private DoneCallback<GenerateC> cb;

		public ProgressiveGenerateFiles_AmazingFunction(final AmazingFunction aAmazingFunction) {
			amazingFunction = aAmazingFunction;
		}

		@Override
		public ProgressiveGenerateFiles_Amazing_ onFileGen(final DoneCallback<GenerateC> aGenerateC) {
			this.cb = aGenerateC;
			return this;
		}

		@Override
		public void with(final GenerateC aGenerateC) {
			Preconditions.checkNotNull(cb);
			cb.onDone(aGenerateC);
		}
	}

	static interface ProgressiveGenerateFiles {
		public static ProgressiveGenerateFiles of(final AmazingFunction amazingFunction) {
			return new ProgressiveGenerateFiles_AmazingFunction(amazingFunction);
		}

		ProgressiveGenerateFiles_Amazing_ onFileGen(DoneCallback<GenerateC> aGenerateC);
	}

	private static class MyGenerateResultSink extends DeadGenerateResultSink {
		private final NG_OutputFunction of;

		public MyGenerateResultSink(final NG_OutputFunction aOf) {
			of = aOf;
		}

		@Override
		public void addFunction(final BaseEvaFunction aGf,
								final List<C2C_Result> aRs,
								final GenerateFiles aGenerateFiles) {
			of.setFunction(aGf, aGenerateFiles, aRs);
		}
	}
}
