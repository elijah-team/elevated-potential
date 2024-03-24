package tripleo.elijah_durable_elevated.elijah.comp;

import io.reactivex.rxjava3.annotations.NonNull;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.Eventual;
import tripleo.elijah.comp.i.ModuleListener;
import tripleo.elijah.g.GWorldModule;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah_durable_elevated.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah_durable_elevated.elijah.comp.notation.GN_PL_Run2;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.DeducePhase;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.GenerateFunctions;
import tripleo.elijah_durable_elevated.elijah.world.i.WorldModule;

class PL_ModuleListener implements ModuleListener {
	private final PipelineLogic        pipelineLogic;
//	@SuppressWarnings("FieldCanBeLocal")
//	private final IPipelineAccess      pa1;
	private final PL_ForModuleListener fml;

	public PL_ModuleListener(final PipelineLogic aPipelineLogic, final IPipelineAccess aPa) {
		pipelineLogic = aPipelineLogic;
//		pa1           = aPa;
		fml = new PL_ForModuleListener(pipelineLogic);
	}

	@Override
	public void close() {
		//NotImplementedException.raise_stop();
	}

	@Override
	public void listen(final GWorldModule module1) {
		final WorldModule module = (WorldModule) module1;
		module.getErq().then(rq -> fml.action(module, rq, pipelineLogic.dp, pipelineLogic.modMap));
	}

	static class PL_ForModuleListener {
		private final PipelineLogic pipelineLogic;

		public PL_ForModuleListener(final PipelineLogic aPipelineLogic) {
			pipelineLogic = aPipelineLogic;
		}

		public void action(final WorldModule module,
						   final GN_PL_Run2.GenerateFunctionsRequest rq,
						   final DeducePhase dp,
						   final @NonNull PipelineLogic.ModMap p) {
			final OS_Module         mod = module.module();
			final GenerateFunctions gfm = pipelineLogic.getGenerateFunctions(mod);

			gfm.generateFromEntryPoints(rq);

			p.then(mod, this::_onDone);
		}

		public void _onDone(final Eventual<DeducePhase.GeneratedClasses> eventual) {
			final DeducePhase.@NotNull GeneratedClasses lgc = pipelineLogic.dp.generatedClasses;
			eventual.resolve(lgc);
		}
	}
}
