package tripleo.elijah_durable_elevated.elijah.nextgen.inputtree;

import tripleo.elijah.g.GWorldModule;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleList;
import tripleo.elijah_durable_elevated.elijah.world.i.WorldModule;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

//import org.jetbrains.annotations.*;
//import tripleo.elijah.comp.*;
//import tripleo.elijah.entrypoints.*;
//import tripleo.elijah.stages.deduce.*;
//import tripleo.elijah.stages.gen_fn.*;
//import tripleo.elijah.stages.gen_generic.*;
//import tripleo.elijah.stages.logging.*;
//import tripleo.elijah.util.*;
//import tripleo.elijah.work.*;
//import tripleo.elijah.world.i.*;
//
//import java.util.*;
//import java.util.function.*;
//import java.util.stream.*;
//
public class EIT_ModuleList_ implements EIT_ModuleList {
	private final List<WorldModule> mods = Collections.EMPTY_LIST; //new ArrayList<>();

//	private void __process__PL__each(final @NotNull _ProcessParams plp) {
//		final List<EvaNode> resolved_nodes = new ArrayList<EvaNode>();
//
//		final WorldModule mod = plp.getMod();
//		final DeducePhase.GeneratedClasses lgc = plp.getLgc();
//
//		// assert lgc.size() == 0;
//
//		final int size = lgc.size();
//
//		if (size != 0) {
//			NotImplementedException.raise();
//			Stupidity.println_err(String.format("lgc.size() != 0: %d", size));
//		}
//
//		plp.generate();
//
//		// assert lgc.size() == epl.size(); //hmm
//
//		final ICodeRegistrar codeRegistrar = plp.pipelineLogic.generatePhase.getCodeRegistrar();
//		assert codeRegistrar != null;
//		final Coder coder = new Coder(codeRegistrar);
//
//		for (final EvaNode evaNode : lgc) {
//			coder.codeNodes(mod, resolved_nodes, evaNode);
//		}
//
//		resolved_nodes.forEach(generatedNode -> coder.codeNode(generatedNode, mod));
//
//		plp.deduceModule();
//
//		// PipelineLogic.resolveCheck(lgc);
//
////			for (final GeneratedNode gn : lgf) {
////				if (gn instanceof EvaFunction) {
////					EvaFunction gf = (EvaFunction) gn;
////					tripleo.elijah.util.Stupidity.println2("----------------------------------------------------------");
////					tripleo.elijah.util.Stupidity.println2(gf.name());
////					tripleo.elijah.util.Stupidity.println2("----------------------------------------------------------");
////					EvaFunction.printTables(gf);
////					tripleo.elijah.util.Stupidity.println2("----------------------------------------------------------");
////				}
////			}
//	}
//
	@Override public void add(final GWorldModule m) {
		mods.add((WorldModule) m);
	}

	@Override public List<GWorldModule> getMods() {
		return Collections.unmodifiableList(mods);
	}

//	public void process__PL(final Function<WorldModule, GenerateFunctions> ggf,
//			final @NotNull PipelineLogic pipelineLogic) {
//		for (final WorldModule mod : mods) {
//			final @NotNull EntryPointList epl = null; // mod.entryPoints;
//
//			//
//			//
//			//
//			//
//			//
//			//
//			// imposed NULL 09/01
//			//
//			//
//			//
//			//
//			//
//			//
//			//
//
//			if (epl.size() == 0) {
//				continue;
//			}
//
//			final GenerateFunctions gfm = ggf.apply(mod);
//
//			final DeducePhase deducePhase = pipelineLogic.dp;
//			// final DeducePhase.@NotNull GeneratedClasses lgc =
//			// deducePhase.generatedClasses;
//
//			final _ProcessParams plp = new _ProcessParams(mod, pipelineLogic, gfm, epl, deducePhase);
//
//			__process__PL__each(plp);
//		}
//	}
//
	@Override
	public Stream<GWorldModule> stream() {
		return mods.stream()
				.map(m -> m);
	}

	//	private static class _ProcessParams {
//		private final @NotNull DeducePhase deducePhase;
//		@NotNull
//		private final EntryPointList epl;
//		private final @NotNull GenerateFunctions gfm;
//		private final WorldModule mod;
//		private final @NotNull PipelineLogic pipelineLogic;
//
//		@Contract(pure = true)
//		private _ProcessParams(@NotNull final WorldModule aModule, @NotNull final PipelineLogic aPipelineLogic,
//				@NotNull final GenerateFunctions aGenerateFunctions, @NotNull final EntryPointList aEntryPointList,
//				@NotNull final DeducePhase aDeducePhase) {
//			mod = aModule;
//			pipelineLogic = aPipelineLogic;
//			gfm = aGenerateFunctions;
//			epl = aEntryPointList;
//			deducePhase = aDeducePhase;
//		}
//
//		public void deduceModule() {
//			deducePhase.deduceModule(mod, getLgc(), getVerbosity());
//		}
//
//		public void generate() {
//			epl.generate(gfm, deducePhase, getWorkManagerSupplier());
//		}
//
//		@Contract(pure = true)
//		public DeducePhase.@NotNull GeneratedClasses getLgc() {
//			return deducePhase.generatedClasses;
//		}
//
//		@Contract(pure = true)
//		public WorldModule getMod() {
//			return mod;
//		}
//
//		@Contract(pure = true)
//		public ElLog.@NotNull Verbosity getVerbosity() {
//			return pipelineLogic.getVerbosity();
//		}
//
//		@Contract(pure = true)
//		public @NotNull Supplier<WorkManager> getWorkManagerSupplier() {
//			return () -> pipelineLogic.generatePhase.getWm();
//		}
//
//	}

}
