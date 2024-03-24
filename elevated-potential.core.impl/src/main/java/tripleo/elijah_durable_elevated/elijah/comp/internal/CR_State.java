/*  -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.elijah.comp.internal;

import org.apache.commons.lang3.tuple.Pair;
import org.jdeferred2.DoneCallback;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.CB_Action;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.notation.GN_Env;
import tripleo.elijah.comp.notation.GN_Notable;
import tripleo.elijah.g.GCR_State;
import tripleo.elijah.g.GPipelineMember;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah_durable_elevated.elijah.comp.*;
import tripleo.elijah_durable_elevated.elijah.comp.i.ProcessRecord;
import tripleo.elijah_durable_elevated.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah_durable_elevated.elijah.comp.nextgen.CK_DefaultStepRunner;
import tripleo.elijah_durable_elevated.elijah.nextgen.output.NG_OutputItem;
import tripleo.elijah_durable_elevated.elijah.stages.gen_c.GenerateC;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.GenerateFiles;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah_durable_elevated.elijah.stages.write_stage.pipeline_impl.WP_Flow;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Consumer;

public class CR_State implements GCR_State {

	private final CompilationEnclosure ce;
	public  CB_Action          cur;
	public        ProcessRecord      pr;
	private final ICompilationAccess ca;
	private       CompilationRunner  compilationRunner;

	@Contract(pure = true)
	public CR_State(ICompilationAccess aCa) {
		ca = aCa;
		pr = new ProcessRecordImpl(ca);
		ce = (CompilationEnclosure) ca.getCompilationEnclosure();

		ce._resolvePipelineAccessPromise(new ProcessRecord_PipelineAccess());
	}

	public CompilationEnclosure getCompilationEnclosure() {
		return ce;
	}

	public ICompilationAccess ca() {
		return ca;
	}

	public CompilationRunner runner() {
		return compilationRunner;
	}

	public void setRunner(CompilationRunner aCompilationRunner) {
		compilationRunner = aCompilationRunner;
	}

	private static class ProcessRecordImpl implements ProcessRecord {
		// private final DeducePipeline dpl;
		private final @NotNull ICompilationAccess ca;
		private                IPipelineAccess    pa;
		private @NotNull       PipelineLogic      pipelineLogic;

		public ProcessRecordImpl(final @NotNull ICompilationAccess ca0) {
			ca = ca0;

			final CompilationEnclosure compilationEnclosure0 = (CompilationEnclosure) ca0.getCompilationEnclosure();
			compilationEnclosure0.getPipelineAccessPromise().then(pa0 -> {
				pa = pa0;
				pipelineLogic = new PipelineLogic(pa, ca);
			});
		}

		@Contract(pure = true)
		@Override
		public ICompilationAccess ca() {
			return ca;
		}

		@Contract(pure = true)
		@Override
		public IPipelineAccess pa() {
			return pa;
		}

		@Contract(pure = true)
		@Override
		public PipelineLogic pipelineLogic() {
			return pipelineLogic;
		}

		@Override
		public void writeLogs() {
			final CompilationEnclosure ce            = pa.getCompilationEnclosure();
			ce.writeLogs();
		}
	}

	class ProcessRecord_PipelineAccess implements IPipelineAccess {
		private final @NotNull List<EvaNode>                                         _l_classes         = new ArrayList<>();
		private final @NotNull List<EvaClass>                                        activeClasses      = new ArrayList<>();
		private final @NotNull List<EvaNamespace>                                    activeNamespaces   = new ArrayList<>();
		private final          DeferredObject<EvaPipeline, Void, Void>               EvaPipelinePromise = new DeferredObject<>();
		private final          Map<OS_Module, DeferredObject<GenerateC, Void, Void>> gc2m_map           = new HashMap<>();
		@SuppressWarnings("rawtypes")
		private final @NotNull Map<Provenance, Pair<Class, Class>>                   installs           = new HashMap<>();
		private final          DeferredObject<List<EvaNode>, Void, Void>             nlp                = new DeferredObject<>();
		private final          List<NG_OutputItem>                                   outputs            = new ArrayList<NG_OutputItem>();
		private final @NotNull DeferredObject<PipelineLogic, Void, Void>             ppl                = new DeferredObject<>();
		@NotNull
		List<BaseEvaFunction> activeFunctions = new ArrayList<BaseEvaFunction>();
		private AccessBus           _ab;
		private WritePipeline       _wpl;
		private GenerateResultSink  grs;
		private List<CompilerInput> inp;

		@Override
		public void _send_GeneratedClass(final EvaNode aClass) {
			_l_classes.add(aClass);
		}

		@Override
		public void _setAccessBus(final AccessBus ab) {
			_ab = ab;
		}

		@Override
		public void activeClass(final EvaClass aEvaClass) {
			activeClasses.add(aEvaClass);
		}

		@Override
		public void activeFunction(final BaseEvaFunction aEvaFunction) {
			activeFunctions.add(aEvaFunction);
		}

		@Override
		public void activeNamespace(final EvaNamespace aEvaNamespace) {
			activeNamespaces.add(aEvaNamespace);
		}

		@Override
		public void addLog(final ElLog aLOG) {
			getCompilationEnclosure().addLog(aLOG);
		}

		@Override
		public void addOutput(final NG_OutputItem aOutput) {
			this.outputs.add(aOutput);
		}

		@Override
		public AccessBus getAccessBus() {
			return _ab;
		}

		@Override
		public @NotNull List<EvaClass> getActiveClasses() {
			return activeClasses;
		}

		@Override
		public @NotNull List<BaseEvaFunction> getActiveFunctions() {
			return activeFunctions;
		}

		@Override
		public @NotNull List<EvaNamespace> getActiveNamespaces() {
			return activeNamespaces;
		}

		@Override
		public Compilation getCompilation() {
			return (Compilation) ca.getCompilation();
		}

		@Override
		public CompilationEnclosure getCompilationEnclosure() {
			return getCompilation().getCompilationEnclosure();
		}

		@Override
		public List<CompilerInput> getCompilerInput() {
			return getCompilationEnclosure().getCompilerInput();
		}

		@Override
		public GenerateResultSink getGenerateResultSink() {
			return grs;
		}

		@Override
		public @NotNull DeferredObject<PipelineLogic, Void, Void> getPipelineLogicPromise() {
			return ppl;
		}

		@Override
		public ProcessRecord getProcessRecord() {
			return pr;
		}

		// @Override
		// public @NotNull List<NG_OutputItem> getOutputs() {
		// return outputs;
		// }

		@Override
		public WritePipeline getWitePipeline() {
			return _wpl;
		}

		@Override
		public void install_notate(final Provenance aProvenance,
								   final Class<? extends GN_Notable> aRunClass,
								   final Class<? extends GN_Env> aEnvClass) {
			installs.put(aProvenance, Pair.of(aRunClass, aEnvClass));
		}

		@Override
		public void notate(final Provenance aProvenance, final GN_Env aGNEnv) {
			var y = installs.get(aProvenance);
			// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("210 "+y);

			Class<?> x = y.getLeft();
			// var z = y.getRight();

			try {
				var inst = x.getMethod("getFactoryEnv", GN_Env.class);

				var notable1 = inst.invoke(null, aGNEnv);

				if (notable1 instanceof @NotNull GN_Notable notable) {
					final NotableAction notableAction = new NotableAction(notable);

					// cb.add(notableAction);

					notableAction._actual_run();  // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee

					// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("227 "+inst);
				}
			} catch (NoSuchMethodException | SecurityException | InvocationTargetException | IllegalAccessException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		@Override
		public void notate(final Provenance provenance, final @NotNull GN_Notable aNotable) {
			var cb = getCompilationEnclosure().getCompilationBus();

			// FIXME 07/01 this doesn't work, why??
			// also keep in mind `provenance'
			final NotableAction notableAction = new NotableAction(aNotable);

			cb.add(notableAction);

			// 09/01 aNotable.run();
			notableAction._actual_run();
		}

		@Override
		public PipelineLogic pipelineLogic() {
			return getProcessRecord().pipelineLogic();
		}

		@Override
		public void registerNodeList(final DoneCallback<List<EvaNode>> done) {
			nlp.then(done);
		}

		@Override
		public void resolvePipelinePromise(final PipelineLogic aPipelineLogic) {
			this._ab.resolvePipelineLogic(aPipelineLogic);
		}

		@Override
		public void resolveWaitGenC(final OS_Module mod, final GenerateFiles aGenerateFiles) {
			final GenerateC                       gc  = (GenerateC) aGenerateFiles;
			DeferredObject<GenerateC, Void, Void> gcp = new DeferredObject<>();
			gcp.resolve(gc);
			gc2m_map.put(mod, gcp);
		}

		@Override
		public void setGenerateResultSink(final GenerateResultSink aGenerateResultSink) {
			grs = aGenerateResultSink;
		}

		@Override
		public void setNodeList(final @NotNull List<EvaNode> aEvaNodeList) {
			nlp/* ;) */.resolve(new ArrayList<>(aEvaNodeList));
		}

		@Override
		public void setWritePipeline(final WritePipeline aWritePipeline) {
			_wpl = aWritePipeline;
		}

		@Override
		public void waitGenC(final OS_Module mod, final Consumer<GenerateC> cb) {
			final DeferredObject<GenerateC, Void, Void> v = gc2m_map.get(mod);
			assert v != null;
			v.then(ggc -> cb.accept(ggc));
		}

		@Override
		public void finishPipeline(final GPipelineMember aPM, final WP_Flow.OPS aOps) {
			final String formatted = "[FinishPipeline] %s %s".formatted(aPM.finishPipeline_asString(), aOps);
			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4(formatted);
		}

		@Override
		public void runStepsNow(final CK_Steps aSteps, final CK_StepsContext aStepsContext) {
			final CK_Monitor monitor = ((CompilationEnclosure) ca.getCompilationEnclosure()).getDefaultMonitor();
			CK_DefaultStepRunner.runStepsNow(aSteps, aStepsContext, monitor);
		}

		@Override
		public void addFunctionStatement(final EG_Statement aStatement) {
			if (aStatement instanceof FunctionStatement fs) {
				addFunctionStatement(fs);
			}
		}

		@Override
		public void subscribePipelineLogic(final AccessBus.@NotNull AB_PipelineLogicListener aListener) {
			final AccessBus ab = getAccessBus();
			ab.subscribePipelineLogic(aListener::pl_slot);
		}

		@Override
		public void subscribe_lgc(final AccessBus.@NotNull AB_LgcListener aListener) {
			final AccessBus ab = getAccessBus();
			ab.subscribe_lgc(aListener);
		}

		public void addFunctionStatement(final FunctionStatement aFunctionStatement) {
			EvaPipelinePromise.then(gp -> {
				gp.addFunctionStatement(aFunctionStatement);
			});
		}

		@Override
		public void setCompilerInput(final List<CompilerInput> aInputs) {
			getCompilationEnclosure().setCompilerInput(aInputs);
		}

		@Override
		public void setEvaPipeline(final @NotNull EvaPipeline agp) {
			if (EvaPipelinePromise.isResolved()) {
				EvaPipelinePromise.then(ep -> {
					assert ep == agp;
				});
			} else {
				EvaPipelinePromise.resolve(agp);
			}
		}
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
