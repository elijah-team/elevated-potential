package tripleo.elijah_elevateder.stages.deduce.pipeline_impl;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_elevated_durable.pipelines.PipelineLogic;
import tripleo.elijah_elevateder.comp.i.extra.IPipelineAccess;
import tripleo.elijah_elevateder.stages.deduce.DeducePhase;

class PL_SaveGeneratedClasses implements PipelineLogicRunnable {
	private final IPipelineAccess pa;

	@Contract(pure = true)
	public PL_SaveGeneratedClasses(final IPipelineAccess aPa) {
		pa = aPa;
	}

	@Override
	public void run(final @NotNull PipelineLogic pipelineLogic) {
		final DeducePhase deducePhase = pipelineLogic.dp;

		deducePhase.country().sendClasses(pa::setNodeList);
	}
}
