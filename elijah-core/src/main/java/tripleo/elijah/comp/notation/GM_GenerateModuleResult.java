package tripleo.elijah.comp.notation;

import org.jetbrains.annotations.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.work.*;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public record GM_GenerateModuleResult(GenerateResult generateResult, GN_GenerateNodesIntoSink generateNodesIntoSink,
		GM_GenerateModuleRequest generateModuleRequest, Supplier<GenerateResultEnv> figs) {
	void doResult(final @NotNull WorkManager wm) {
		// TODO find GenerateResultEnv and centralise them
		final WorkList wl = new WorkList();
		final GenerateFiles generateFiles1 = generateModuleRequest.getGenerateFiles(figs);
		final GenerateResult gr = gr();

		generateFiles1.finishUp(gr, wm, wl);

		wm.addJobs(wl);
		wm.drain();

		gr.additional(generateResult);
	}

	private GenerateResult gr() {
		return generateNodesIntoSink._env().gr();
	}
}
