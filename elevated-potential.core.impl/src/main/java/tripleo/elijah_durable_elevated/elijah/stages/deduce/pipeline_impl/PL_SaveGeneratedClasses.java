package tripleo.elijah_durable_elevated.elijah.stages.deduce.pipeline_impl;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_elevated.elijah.comp.PipelineLogic;
import tripleo.elijah_durable_elevated.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.DeducePhase;

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
