package tripleo.elijah.test_help;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.IO;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.comp.StdErrSink;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.comp.i.ProcessRecord;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.notation.GM_GenerateModule;
import tripleo.elijah.comp.notation.GM_GenerateModuleRequest;
import tripleo.elijah.comp.notation.GN_GenerateNodesIntoSink;
import tripleo.elijah.comp.notation.GN_GenerateNodesIntoSinkEnv;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.impl.OS_ModuleImpl;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleList;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.gen_generic.pipeline_impl.DefaultGenerateResultSink;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah.stages.gen_generic.pipeline_impl.ProcessedNode;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.work.WorkList;
import tripleo.elijah.work.WorkManager;
import tripleo.elijah.world.impl.DefaultWorldModule;

import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

// TODO replace with CompilationFlow
public class Boilerplate {
	public Compilation        comp;
	public ICompilationAccess aca;
	public ProcessRecord      pr;
	public PipelineLogic      pipelineLogic;
	public GenerateFiles      generateFiles;
	tripleo.elijah.lang.i.OS_Module module;
	private CompilationRunner cr;

	public OS_Module defaultMod() {
		if (module == null) {
			module = new OS_ModuleImpl();
			if (comp != null)
				module.setParent(comp);
		}

		return module;
	}

	public void get() {
		comp = new CompilationImpl(new StdErrSink(), new IO());
		final ICompilationAccess aca1 = ((CompilationImpl) comp)._access();
		aca = aca1 != null ? aca1 : new DefaultCompilationAccess(comp);

		CR_State crState;
		crState = new CR_State(aca);
		cr = new CompilationRunner(aca, crState, () -> new DefaultCompilationBus(aca.getCompilation().getCompilationEnclosure()));
		crState.setRunner(cr);

		comp.getCompilationEnclosure().setCompilationRunner(cr);

		//crState = comp.getCompilationEnclosure().getCompilationRunner().crState;
		crState.ca();
		assert comp.getCompilationEnclosure().getCompilationRunner().getCrState() != null; // always true

		pr = cr.getCrState().pr;
		pipelineLogic = pr.pipelineLogic();

		if (module != null) {
			module.setParent(comp);
		}
	}

	public DeducePhase getDeducePhase() {
		return pr.pipelineLogic().dp;
	}

	public void getGenerateFiles(final @NotNull OS_Module mod) {
		if (generateFiles == null) {
			List<ProcessedNode>        lgc         = List_of();
			IPipelineAccess            pa          = pipelineLogic().dp.pa;
			GenerateResultSink         resultSink1 = new DefaultGenerateResultSink(pa);
			EIT_ModuleList             moduleList  = pipelineLogic().mods();
			ElLog.Verbosity            verbosity   = ElLog.Verbosity.SILENT;
			Old_GenerateResult         gr          = new Old_GenerateResult();
			final CompilationEnclosure ce          = comp.getCompilationEnclosure();
//			CompilationEnclosure ce          = pa.getCompilationEnclosure();

			final GN_GenerateNodesIntoSinkEnv generateNodesIntoSinkEnv = new GN_GenerateNodesIntoSinkEnv(lgc, resultSink1, moduleList, verbosity, gr, pa, ce);

			var generateNodesIntoSink = new GN_GenerateNodesIntoSink(generateNodesIntoSinkEnv);

			var worldModule = new DefaultWorldModule(mod, ce);
			var workManager = new WorkManager();
			var workList    = new WorkList();

			final GM_GenerateModuleRequest gmr     = new GM_GenerateModuleRequest(generateNodesIntoSink, worldModule, generateNodesIntoSinkEnv);
			GenerateResultEnv              fileGen = new GenerateResultEnv(resultSink1, gr, workManager, workList, new GM_GenerateModule(gmr));

			var wm = new DefaultWorldModule(mod, ce);

			final @NotNull String         lang   = Compilation.CompilationAlways.defaultPrelude();
			final OutputFileFactoryParams params = new OutputFileFactoryParams(wm, ce);

			generateFiles = OutputFileFactory.create(lang, params, fileGen);
		}
	}

	public PipelineLogic pipelineLogic() {
		return pipelineLogic;
	}
}
