package tripleo.elijah_durable_elevated.elijah.comp.notation;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.work.WorkManager;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.EvaNode;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.*;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.pipeline_impl.*;
import tripleo.elijah_durable_elevated.elijah.work.WorkList__;

import java.util.List;
import java.util.function.Supplier;

public class GM_GenerateModule {
	private final GM_GenerateModuleRequest gmr;

	public GM_GenerateModule(final GM_GenerateModuleRequest aGmr) {
		gmr = aGmr;
	}

	public GM_GenerateModuleResult getModuleResult(
			final @NotNull WorkManager wm,
			final @NotNull GenerateResultSink aResultSink
	) {
		final OS_Module mod = gmr.params().getMod();
		final @NotNull GN_GenerateNodesIntoSink generateNodesIntoSink = gmr.generateNodesIntoSink();

		final GenerateResult gr1 = new Sub_GenerateResult();
		final Supplier<GenerateResultEnv> fgs =
		//this::createGenerateResultEnv;
		()->
		new GenerateResultEnv(aResultSink, gr1, wm, new WorkList__() /* tautology */, this);

		final @NotNull GenerateFiles ggc = gmr.getGenerateFiles(fgs);
		final List<ProcessedNode>    lgc = generateNodesIntoSink._env().lgc();

		var fileGen = ggc.getFileGen();

		for (ProcessedNode processedNode : lgc) {
			final EvaNode evaNode = ((ProcessedNode1) processedNode).getEvaNode();

			if (!(processedNode.matchModule(mod)))
				continue; // README curious

			if (processedNode.isContainerNode()) {
				processedNode.processContainer(ggc, fileGen);

				processedNode.processConstructors(ggc, fileGen);
				processedNode.processFunctions(ggc, fileGen);
				processedNode.processClassMap(ggc, fileGen);
			} else {
				logProgress(2009, evaNode.getClass().getName());
			}
		}

		return new GM_GenerateModuleResult(gr1, generateNodesIntoSink, gmr, fgs);
	}

	public GM_GenerateModuleRequest gmr() {
		return gmr;
	}

// README 12/30 too slick	
//	public void createGenerateResultEnv() {
//		return new GenerateResultEnv(aResultSink, gr1, wm, new WorkList__() /* tautology */, this);
//	}
	
	public void logProgress(int code, String message) {
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_2(code+" " + message);
	}
}
