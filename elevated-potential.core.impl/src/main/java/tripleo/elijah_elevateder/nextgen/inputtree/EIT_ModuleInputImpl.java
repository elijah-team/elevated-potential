package tripleo.elijah_elevateder.nextgen.inputtree;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.*;
import tripleo.elijah.nextgen.inputtree.EIT_InputType;
import tripleo.elijah_elevateder.comp.i.Compilation;
import tripleo.elijah_elevated_durable.pipelines.EvaPipeline;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;
import tripleo.elijah.comp.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah_elevateder.comp.notation.GM_GenerateModule;
import tripleo.elijah_elevateder.comp.notation.GM_GenerateModuleRequest;
import tripleo.elijah_elevateder.comp.notation.GN_GenerateNodesIntoSink;
import tripleo.elijah_elevateder.comp.notation.GN_GenerateNodesIntoSinkEnv;
import tripleo.elijah.g.GModuleGenerationRequest;
import tripleo.elijah.lang.i.ModuleItem;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.model.SM_Module;
import tripleo.elijah.nextgen.model.SM_ModuleItem;
import tripleo.elijah_elevateder.stages.gen_c.GenerateC;
import tripleo.elijah_elevateder.stages.gen_fn.EvaNode;

import tripleo.elijah_elevateder.stages.gen_generic.*;
import tripleo.elijah_elevateder.stages.gen_generic.pipeline_impl.DefaultGenerateResultSink;
import tripleo.elijah.work.*;
import tripleo.elijah_elevateder.work.EDL_WorkList;
import tripleo.elijah_elevateder.world.i.WorldModule;

import java.util.*;
import java.util.function.Consumer;

public class EIT_ModuleInputImpl implements EIT_ModuleInput {
	private final Compilation c;
	private final OS_Module    module;

	@Contract(pure = true)
	public EIT_ModuleInputImpl(final OS_Module aModule, final Compilation aC) {
		module = aModule;
		c      = aC;
	}

	@Override
	public @NotNull SM_Module computeSourceModel() {
		final SM_Module sm = new SM_Module() {
			@Override
			public @NotNull List<SM_ModuleItem> items() {
				final List<SM_ModuleItem> items = new ArrayList<>();
				for (final ModuleItem item : module.getItems()) {
					items.add(new SM_ModuleItem() {
						@Override
						public ModuleItem _carrier() {
							return item;
						}
					});
				}
				return items;
			}
		};
		return sm;
	}

	@Override
	public void doGenerate(final GModuleGenerationRequest r0) {
		final ModuleGenerationRequest r = (ModuleGenerationRequest) r0;
		doGenerate(r.getEvaNodeList(), r.work(), r.getGenerateResultConsumer(), r.getCompilationEnclosure());
	}

	private void doGenerate(final List<EvaNode> nodes,
						   final WorkManager wm,
						   final @NotNull Consumer<GenerateResult> resultConsumer,
						   final @NotNull CompilationEnclosure ce) {
		// 0. get lang
		final String lang = langOfModule();

		// 1. find Generator (GenerateFiles) eg. GenerateC final
		final WorldModule             mod           = ce.getCompilation().world().findModule(module);
		final OutputFileFactoryParams p             = new OutputFileFactoryParams(mod, ce);

		var resultSink = new DefaultGenerateResultSink(c.pa());
		var gr         = new Old_GenerateResult();
		var wl         = new EDL_WorkList();
		var nodes1     = EvaPipeline.processLgc(nodes);
		var gnis_env   = new GN_GenerateNodesIntoSinkEnv(nodes1,
														 resultSink,
														 ce.getPipelineLogic().getVerbosity(),
														 gr,
														 ce);
		var gnis       = new GN_GenerateNodesIntoSink(gnis_env);
		var gmr        = new GM_GenerateModuleRequest(gnis, mod, gnis_env);
		var gmgm       = new GM_GenerateModule(gmr);
		var env        = new GenerateResultEnv(resultSink, gr, wm, wl, gmgm);

		final GenerateFiles           generateFiles = OutputFileFactory.create(lang, p, env);

		// 2. query results
		final GenerateResult gr2 = generateFiles.resultsFromNodes(nodes, wm, ((GenerateC) generateFiles).resultSink, env);

		// 3. #drain workManager -> README part of workflow. may change later as appropriate
		wm.drain();

		// 4. tail process results
		resultConsumer.accept(gr2);
	}

	private String langOfModule() {
		final LibraryStatementPart lsp  = module.getLsp();
		final CompilerInstructions ci   = lsp.getInstructions();
		final Optional<String>     s    = ci.genLang();
		if (s.isPresent()) {
			return s.get();
		} else
			return null;
	}

	@Override
	public @NotNull EIT_InputType getType() {
		return EIT_InputType.ELIJAH_SOURCE;
	}
}
