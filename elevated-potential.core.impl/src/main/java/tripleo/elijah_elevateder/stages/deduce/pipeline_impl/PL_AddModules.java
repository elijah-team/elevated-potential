package tripleo.elijah_elevateder.stages.deduce.pipeline_impl;

import org.jetbrains.annotations.*;
import tripleo.elijah_elevated_durable.pipelines.PipelineLogic;
import tripleo.elijah_elevateder.comp.i.extra.IPipelineAccess;
import tripleo.elijah_elevateder.world.i.WorldModule;
import tripleo.elijah_fluffy.diagnostic.Diagnostic;
import tripleo.elijah_fluffy.util.*;

class PL_AddModules implements PipelineLogicRunnable {
	private final Eventual<PipelineLogic> plp = new Eventual<>();

	@Contract(pure = true)
	public PL_AddModules(final @NotNull IPipelineAccess aPipelineAccess) {
		var w = aPipelineAccess.getCompilation().world();

		w.addModuleProcess(new CompletableProcess<>() {
			@Override
			public void add(final WorldModule item) {
//				plp.then(pipelineLogic -> pipelineLogic.addModule(item));
				SimplePrintLoggerToRemoveSoon.println_err_4("[PL_AddModules::ModuleCompletableProcess] add "+item.module().getFileName());
			}

			@Override
			public void complete() {
//				NotImplementedException.raise_stop();
			}

			@Override
			public void error(final Diagnostic d) {
			//	throw new UnintendedUseException();
			}

			@Override
			public void preComplete() {
				//throw new UnintendedUseException();
			}

			@Override
			public void start() {
				SimplePrintLoggerToRemoveSoon.println_out_4("[PL_AddModules::ModuleCompletableProcess] start");
			}
		});
	}

	@Override
	public void run(final @NotNull PipelineLogic pipelineLogic) {
		plp.resolve(pipelineLogic);
	}
}
