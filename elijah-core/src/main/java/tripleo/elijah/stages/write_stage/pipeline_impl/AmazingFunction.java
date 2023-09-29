package tripleo.elijah.stages.write_stage.pipeline_impl;

import org.jetbrains.annotations.*;
import tripleo.elijah.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.notation.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.output.*;
import tripleo.elijah.stages.garish.*;
import tripleo.elijah.stages.gen_c.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.gen_generic.pipeline_impl.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah.work.*;
import tripleo.elijah.world.i.*;
import tripleo.util.buffer.*;

import java.util.*;

import static tripleo.elijah.util.Helpers.*;

class AmazingFunction implements Amazing {
	private final NG_OutputFunction                of;
	private final BaseEvaFunction                  f;
	private final OS_Module                        mod;
	private final WPIS_GenerateOutputs.OutputItems itms;
	private final GenerateResult                   result;
	private final IPipelineAccess pa;

	public AmazingFunction(final @NotNull BaseEvaFunction aBaseEvaFunction,
	                       final @NotNull WPIS_GenerateOutputs.OutputItems aOutputItems, final @NotNull GenerateResult aGenerateResult,
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
//			EIT_ModuleList eitModuleList = aPipelineLogic.mods();
			Object               eitModuleList = null;
			GenerateResult       gr            = result; // new Old_GenerateResult();
			CompilationEnclosure ce            = pa.getCompilationEnclosure();

			var env = new GN_GenerateNodesIntoSinkEnv(
					List_of(),
					generateResultSink,
					eitModuleList,
					ElLog.Verbosity.VERBOSE,
					gr,
					pa,
					ce
			);

			var world = ce.getCompilation().world();
			var wm    = world.findModule(mod);

			var generateModuleRequest = new GM_GenerateModuleRequest(new GN_GenerateNodesIntoSink(env), wm, env);
			var generateModule        = new GM_GenerateModule(generateModuleRequest);

			var fileGen = new GenerateResultEnv(new MyGenerateResultSink(of), result, new WorkManager(), new WorkList(),
			                                    generateModule
			);

			var generateModuleResult = generateModule.getModuleResult(fileGen.wm(), fileGen.resultSink());

			if (f instanceof EvaFunction ff) {
				ggc.generateCodeForMethod(fileGen, ff);
			} else if (f instanceof EvaConstructor fc) {
				ggc.generateCodeForConstructor(fileGen, fc);
			}

			itms.addItem(of);
		});
	}

	private static class MyGenerateResultSink implements GenerateResultSink {
		private final NG_OutputFunction of;

		public MyGenerateResultSink(final NG_OutputFunction aOf) {
			of = aOf;
		}

		@Override
		public void add(final EvaNode node) {
			throw new UnintendedUseException();
		}

		@Override
		public void addClass_0(final GarishClass aGarishClass, final Buffer aImplBuffer, final Buffer aHeaderBuffer) {
			throw new UnintendedUseException();
		}

		@Override
		public void addClass_1(final @NotNull GarishClass aGarishClass,
		                       final @NotNull GenerateResult aGenerateResult,
		                       final @NotNull GenerateFiles aGenerateFiles) {
			throw new UnintendedUseException();
		}

		@Override
		public void addFunction(final BaseEvaFunction aGf,
		                        final List<C2C_Result> aRs,
		                        final GenerateFiles aGenerateFiles) {
			of.setFunction(aGf, aGenerateFiles, aRs);
		}

		@Override
		public void additional(final GenerateResult aGenerateResult) {
			throw new UnintendedUseException();
		}

		@Override
		public void addNamespace_0(final GarishNamespace aLivingNamespace, final Buffer aImplBuffer,
		                           final Buffer aHeaderBuffer) {
			throw new UnintendedUseException();
		}

		@Override
		public void addNamespace_1(final GarishNamespace aGarishNamespace, final GenerateResult aGenerateResult,
		                           final GenerateC aGenerateC) {
			throw new UnintendedUseException();
		}

		@Override
		public @Nullable LivingClass getLivingClassForEva(final EvaClass aEvaClass) {
			throw new UnintendedUseException();
		}

		@Override
		public @Nullable LivingNamespace getLivingNamespaceForEva(final EvaNamespace aEvaClass) {
			throw new UnintendedUseException();
		}
	}
}
