package tripleo.elijah_elevateder.test_help;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleList;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_elevated_durable.compilation_bus.EDL_CompilationRunner;
import tripleo.elijah_elevated_durable.lang_impl.OS_ModuleImpl;
import tripleo.elijah_elevated_durable.pipelines.PipelineLogic;
import tripleo.elijah_elevated_durable.world_impl.DefaultWorldModule;
import tripleo.elijah_elevateder.comp.i.Compilation;
import tripleo.elijah_elevateder.comp.i.ProcessRecord;
import tripleo.elijah_elevateder.comp.i.extra.IPipelineAccess;
import tripleo.elijah_elevateder.comp.notation.*;
import tripleo.elijah_elevateder.stages.deduce.DeducePhase;
import tripleo.elijah_elevateder.stages.gen_generic.*;
import tripleo.elijah_elevateder.stages.gen_generic.pipeline_impl.*;
import tripleo.elijah_elevateder.stages.logging.ElLog_;
import tripleo.elijah_elevateder.work.EDL_WorkList;
import tripleo.elijah_elevateder.work.EDL_WorkManager;

import java.util.List;

import static tripleo.elijah_fluffy.util.Helpers.List_of;

// TODO replace with CompilationFlow
public class Boilerplate {
	public Compilation0       comp;
	public ICompilationAccess aca;
	public ProcessRecord pr;
	public PipelineLogic pipelineLogic;
	public GenerateFiles generateFiles;
	tripleo.elijah.lang.i.OS_Module module;
	private EDL_CompilationRunner cr;

	public OS_Module defaultMod() {
		if (module == null) {
			module = new OS_ModuleImpl();
			if (comp != null)
				module.setParent(comp);
		}

		return module;
	}

	public void get() {
		comp = new EDL_Compilation(new EDL_ErrSink(), new EDL_IO());
		final ICompilationAccess aca1 = ((EDL_Compilation) comp)._access();
		aca = aca1 != null ? aca1 : new EDL_CompilationAccess((Compilation) comp);

		CR_State crState;
		crState = new CR_State(aca);
		cr = new EDL_CompilationRunner(aca, crState,
		                               () -> new EDL_CompilationBus((@NotNull CompilationEnclosure) aca.getCompilation().getCompilationEnclosure()));
		crState.setRunner(cr);

		final CompilationEnclosure compilationEnclosure = (CompilationEnclosure) comp.getCompilationEnclosure();

		compilationEnclosure.setCompilationRunner(cr);

		// crState = comp.getCompilationEnclosure().getCompilationRunner().crState;
		crState.ca();
		assert compilationEnclosure.getCompilationRunner().getCrState() != null; // always true

		pr = cr.getCrState().pr;
		pipelineLogic = pr.pipelineLogic();

		if (module != null) {
			module.setParent(comp);
		}
	}

	public DeducePhase getDeducePhase() {
		return pr.pipelineLogic().dp;
	}

	public GenerateFiles getGenerateFiles2(final @NotNull OS_Module mod) {
		getGenerateFiles(mod);
		return generateFiles;
	}

	public void getGenerateFiles(final @NotNull OS_Module mod) {
		if (generateFiles == null) {
			List<ProcessedNode> lgc = List_of();
			IPipelineAccess pa = pipelineLogic().dp.pa;
			GenerateResultSink    resultSink1 = new DefaultGenerateResultSink(pa);
			final CompilationEnclosure ce = (CompilationEnclosure) comp.getCompilationEnclosure();
			EIT_ModuleList        moduleList  = ce.getModuleList();
			//Object             moduleList = null;
			ElLog_.Verbosity   verbosity = ElLog_.Verbosity.SILENT;
			Old_GenerateResult gr        = new Old_GenerateResult();
//			CompilationEnclosure ce          = pa.getCompilationEnclosure();

			final GN_GenerateNodesIntoSinkEnv generateNodesIntoSinkEnv = new GN_GenerateNodesIntoSinkEnv(
					lgc,
					resultSink1,
					verbosity,
					gr,
					ce
			);

			var generateNodesIntoSink = new GN_GenerateNodesIntoSink(generateNodesIntoSinkEnv);

			var worldModule = new DefaultWorldModule(mod, ce);
			var workManager = new EDL_WorkManager();
			var workList = new EDL_WorkList();

			final GM_GenerateModuleRequest gmr = new GM_GenerateModuleRequest(generateNodesIntoSink, worldModule,
					generateNodesIntoSinkEnv);
			GenerateResultEnv fileGen = new GenerateResultEnv(resultSink1, gr, workManager, workList,
			                                                  new GM_GenerateModule(gmr));

			var wm = new DefaultWorldModule(mod, ce);

			final @NotNull String lang = EDL_Compilation.CompilationAlways.defaultPrelude();
			final OutputFileFactoryParams params = new OutputFileFactoryParams(wm, ce);

			generateFiles = OutputFileFactory.create(lang, params, fileGen);
		}
	}

	public PipelineLogic pipelineLogic() {
		return pipelineLogic;
	}
}
