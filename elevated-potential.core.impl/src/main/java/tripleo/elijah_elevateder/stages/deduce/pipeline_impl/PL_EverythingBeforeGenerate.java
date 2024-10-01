package tripleo.elijah_elevateder.stages.deduce.pipeline_impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_elevated_durable.pipelines.PipelineLogic;

class PL_EverythingBeforeGenerate implements PipelineLogicRunnable {
	@Override
	public void run(final @NotNull PipelineLogic pipelineLogic) {
		final PipelineLogic.ModuleCompletableProcess mcp = pipelineLogic._mcp();

		var ce = pipelineLogic._pa().getCompilationEnclosure();
		ce.getCompilation().world().addModuleProcess(mcp);
	}
}
