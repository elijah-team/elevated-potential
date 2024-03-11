package tripleo.elijah_durable_elevated.elijah.comp.notation;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.Eventual;
import tripleo.elijah.EventualRegister;
import tripleo.elijah.comp.notation.GN_Env;
import tripleo.elijah.comp.notation.GN_Notable;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah_durable_elevated.elijah.comp.PipelineLogic;
import tripleo.elijah_durable_elevated.elijah.factory.NonOpinionatedBuilder;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.DeducePhase;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.DefaultClassGenerator;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.IClassGenerator;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah_durable_elevated.elijah.stages.inter.ModuleThing;
import tripleo.elijah_durable_elevated.elijah.world.i.WorldModule;
import tripleo.elijah_durable_elevated.elijah.world.impl.DefaultWorldModule;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;

import java.util.Objects;
import java.util.function.Consumer;

public class GN_PL_Run2 implements GN_Notable, EventualRegister {
	private final NonOpinionatedBuilder __nob;

	private final @NotNull WorldModule          mod;
	private final          PipelineLogic        pipelineLogic;
	private final          CompilationEnclosure ce;

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
		final DefaultWorldModule       worldModule = (DefaultWorldModule) mod;
		final GenerateFunctionsRequest rq          = new GenerateFunctionsRequest(dcg, worldModule);

		worldModule.setRq(rq);

		final Eventual<DeducePhase.GeneratedClasses> plgc = pipelineLogic.handle(rq);
		plgc.register(pipelineLogic);

		plgc.then(lgc -> {
			final ICodeRegistrar cr              = dcg.getCodeRegistrar();
			final ResolvedNodes  resolved_nodes2 = new ResolvedNodes(cr);

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
