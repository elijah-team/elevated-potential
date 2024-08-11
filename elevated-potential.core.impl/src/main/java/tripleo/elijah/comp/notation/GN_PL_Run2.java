package tripleo.elijah.comp.notation;

import java.util.*;
import java.util.function.*;

import org.jetbrains.annotations.*;

import tripleo.elijah.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.factory.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.inter.*;
import tripleo.elijah.world.i.*;
import tripleo.elijah.world.impl.*;
import tripleo.elijah_elevated.comp.backbone.*;

public class GN_PL_Run2 implements GN_Notable, EventualRegister {
	private final NonOpinionatedBuilder __nob;

	private final @NotNull WorldModule mod;
	private final PipelineLogic        pipelineLogic;
	private final CompilationEnclosure ce;

	private final DefaultClassGenerator dcg;

	private final Consumer<WorldModule> worldConsumer;

	@Contract(pure = true)
	public GN_PL_Run2(final PipelineLogic aPipelineLogic, final @NotNull WorldModule aMod,
					  final CompilationEnclosure aCe, final Consumer<WorldModule> aWorldConsumer) {
		this(aPipelineLogic, aMod, aCe, aWorldConsumer, new NonOpinionatedBuilder());
	}

	@Contract(pure = true)
	public GN_PL_Run2(final PipelineLogic aPipelineLogic, final @NotNull WorldModule aMod,
			final CompilationEnclosure aCe, final Consumer<WorldModule> aWorldConsumer,
			final @NotNull NonOpinionatedBuilder aNob) {
		pipelineLogic = aPipelineLogic;
		mod = aMod;
		ce = aCe;
		worldConsumer = aWorldConsumer;
		__nob = aNob;

		dcg = new DefaultClassGenerator(pipelineLogic.dp, __nob);
	}

	private void _finish() {
		pipelineLogic.checkFinishEventuals();
	}

	@Override
	public void checkFinishEventuals() {

	}

	@Override
	public <P> void register(final Eventual<P> e) {

	}

	@Override
	public void run() {
		final DefaultWorldModule worldModule = (DefaultWorldModule) mod;
		final GenerateFunctionsRequest rq = new GenerateFunctionsRequest(dcg, worldModule);

		worldModule.setRq(rq);

		final Eventual<DeducePhase.GeneratedClasses> plgc = pipelineLogic.handle(rq);
		plgc.register(pipelineLogic);

		plgc.then(lgc -> {
			final ICodeRegistrar cr = dcg.getCodeRegistrar();
			final ResolvedNodes resolved_nodes2 = new ResolvedNodes(cr);

			resolved_nodes2.init(lgc);
			resolved_nodes2.part2();
			resolved_nodes2.part3(pipelineLogic, mod, lgc);

			worldConsumer.accept(worldModule);
		});

		_finish();
	}

	@SuppressWarnings("unused")
	public static @NotNull GN_PL_Run2 getFactoryEnv(GN_Env givenEnv) {
		if (givenEnv instanceof GN_PL_Run2_Env actualEnv) {
			return new GN_PL_Run2(actualEnv.pipelineLogic(), actualEnv.mod(), actualEnv.ce(), actualEnv.worldConsumer());
		}
		throw new IllegalStateException("Need better env");
	}


	public record GenerateFunctionsRequest(IClassGenerator classGenerator, DefaultWorldModule worldModule) {
		public ModuleThing mt() {
			return Objects.requireNonNull(worldModule.thing());
		}

		public OS_Module mod() {
			return worldModule.module();
		}

		//@Override
		//public String toString() {
		//	return "GenerateFunctionsRequest[" +
		//			"classGenerator=" + classGenerator + ", " +
		//			"worldModule=" + worldModule + ']';
		//}
	}
}
