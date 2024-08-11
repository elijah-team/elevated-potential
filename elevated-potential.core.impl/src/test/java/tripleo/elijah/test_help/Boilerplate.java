package tripleo.elijah.test_help;

import static tripleo.elijah.util.Helpers.*;

import java.util.*;

import org.jetbrains.annotations.*;

import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.notation.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.gen_generic.pipeline_impl.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah.work.*;
import tripleo.elijah.world.impl.*;
import tripleo.elijah_elevated.comp.backbone.*;

// TODO replace with CompilationFlow
public class Boilerplate {
	public Compilation0       comp;
	public ICompilationAccess aca;
	public ProcessRecord pr;
	public PipelineLogic pipelineLogic;
	public GenerateFiles generateFiles;
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
		comp = new CompilationImpl(new StdErrSink(), new IO_());
		final ICompilationAccess aca1 = ((CompilationImpl) comp)._access();
		aca = aca1 != null ? aca1 : new DefaultCompilationAccess((Compilation) comp);

		CR_State crState;
		crState = new CR_State(aca);
		cr = new CompilationRunner(aca, crState,
				() -> new DefaultCompilationBus(
						(@NotNull CompilationEnclosure) aca.getCompilation().getCompilationEnclosure()));
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
			ElLog_.Verbosity   verbosity  = ElLog_.Verbosity.SILENT;
			Old_GenerateResult gr         = new Old_GenerateResult();
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
			var workManager = new WorkManager__();
			var workList = new WorkList__();

			final GM_GenerateModuleRequest gmr = new GM_GenerateModuleRequest(generateNodesIntoSink, worldModule,
					generateNodesIntoSinkEnv);
			GenerateResultEnv fileGen = new GenerateResultEnv(resultSink1, gr, workManager, workList,
					new GM_GenerateModule(gmr));

			var wm = new DefaultWorldModule(mod, ce);

			final @NotNull String lang = CompilationImpl.CompilationAlways.defaultPrelude();
			final OutputFileFactoryParams params = new OutputFileFactoryParams(wm, ce);

			generateFiles = OutputFileFactory.create(lang, params, fileGen);
		}
	}

	public PipelineLogic pipelineLogic() {
		return pipelineLogic;
	}
}
