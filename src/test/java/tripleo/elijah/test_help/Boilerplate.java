package tripleo.elijah.test_help;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.impl.DefaultCompilationAccess;
import tripleo.elijah.comp.impl.DefaultCompilationBus;
import tripleo.elijah.comp.impl.DefaultCompilerController;
import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah.comp.i.ProcessRecord;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.notation.GM_GenerateModule;
import tripleo.elijah.comp.notation.GM_GenerateModuleRequest;
import tripleo.elijah.comp.notation.GN_GenerateNodesIntoSink;
import tripleo.elijah.comp.notation.GN_GenerateNodesIntoSinkEnv;
import tripleo.elijah.factory.comp.CompilationFactory;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.impl.OS_ModuleImpl;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleList;
import tripleo.elijah.stages.deduce.DeducePhase;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.gen_generic.pipeline_impl.DefaultGenerateResultSink;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah.stages.gen_generic.pipeline_impl.ProcessedNode;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah.work.*;
import tripleo.elijah.world.impl.DefaultWorldModule;

import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

// TODO replace with CompilationFlow
public class Boilerplate {
	public Compilation0       compilation0;
	public ICompilationAccess aca;
	public ProcessRecord pr;
	public PipelineLogic pipelineLogic;
	public GenerateFiles generateFiles;
	tripleo.elijah.lang.i.OS_Module module;
	private CompilationRunner cr;

	public OS_Module defaultMod() {
		if (module == null) {
			module = new OS_ModuleImpl();
			if (compilation0 != null)
				module.setParent(compilation0);
		}

		return module;
	}

	public void get() {
		CompilationImpl compilation = CompilationFactory.mkCompilation(new StdErrSink(), new IO_());
		compilation0 = compilation;

		DefaultCompilerController ctl = new DefaultCompilerController(compilation.getCompilationAccess3());
//		compilation.getCompilationEnclosure().s

		final CompilationEnclosure ce = compilation.getCompilationEnclosure();

		ce.provideCompilationAccess( compilation.con().createCompilationAccess());
		ce.provideCompilationRunner(()->ctl.getCon().createCompilationRunner(ce.getCompilationAccess()));


		aca = ce.getCompilationAccess();
//		ElIntrinsics.
		Preconditions.checkNotNull(aca);

		final CR_State crState = ce.getCompilationRunner().getCrState();

		crState.ca();
		assert ce.getCompilationRunner().getCrState() != null; // always true

		pr = cr.getCrState().pr;
		pipelineLogic = pr.pipelineLogic();

		if (module != null) {
			module.setParent(compilation0);
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
			final CompilationEnclosure ce = (CompilationEnclosure) compilation0.getCompilationEnclosure();
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
