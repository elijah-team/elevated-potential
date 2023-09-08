package tripleo.elijah.stages.deduce.pipeline_impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.world.i.WorldModule;

class PL_EverythingBeforeGenerate implements PipelineLogicRunnable {
	@Override
	public void run(final @NotNull PipelineLogic pipelineLogic) {
		final PipelineLogic.ModuleCompletableProcess mcp = pipelineLogic._mcp();

		mcp.start();

		for (final WorldModule mod : pipelineLogic.mods().getMods()) {
			mcp.add(mod);
		}

		mcp.preComplete();
		mcp.complete();
	}
}
