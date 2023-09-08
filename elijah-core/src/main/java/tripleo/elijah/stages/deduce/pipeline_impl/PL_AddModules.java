package tripleo.elijah.stages.deduce.pipeline_impl;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.world.i.WorldModule;

import java.util.List;

class PL_AddModules implements PipelineLogicRunnable {
	private final List<WorldModule> ml;

	@Contract(pure = true)
	public PL_AddModules(final @NotNull IPipelineAccess aPipelineAccess) {
		ml = aPipelineAccess.getCompilation().livingRepo().modules();
	}

	@Override
	public void run(final @NotNull PipelineLogic pipelineLogic) {
		ml.forEach(pipelineLogic::addModule);
	}
}
