package tripleo.elijah.comp.internal;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.Pipeline.RP_Context_1;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.g.GPipeline;
import tripleo.elijah.util.*;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;
import tripleo.vendor.mal.stepA_mal;
import tripleo.vendor.mal.types;

public class OStageProcess implements RuntimeProcess {
	private static class _AddPipeline__MAL extends types.MalFunction {
		private final CompilationEnclosure ce;

		public _AddPipeline__MAL(final CompilationEnclosure aCompilationEnclosure) {
			this.ce = aCompilationEnclosure;
		}

		@Override
		public types.MalVal apply(final types.@NotNull MalList args) throws types.MalThrowable {
			final types.MalVal a0 = args.nth(0);

			if (a0 instanceof final types.@NotNull MalSymbol pipelineSymbol) {
				// 0. accessors
				final String pipelineName = pipelineSymbol.getName();

				// 1. observe side effect
				final PipelinePlugin pipelinePlugin = ce.getPipelinePlugin(pipelineName);
				if (pipelinePlugin == null)
					return types.False;

				// 2. produce effect
				ce.addPipelinePlugin(pipelinePlugin);
				return types.True;
			} else {
				// TODO exception? errSink??
				return types.False;
			}
		}
	}

	private AccessBus ab;
	// private final ProcessRecord pr;
	private final ICompilationAccess ca;

	private stepA_mal.MalEnv2 env;

	public OStageProcess(final ICompilationAccess aCa) {
		ca = aCa;

		final Compilation         compilation          = (Compilation) ca.getCompilation();
		final CompilationEnclosure compilationEnclosure = compilation.getCompilationEnclosure();
		compilationEnclosure.getAccessBusPromise().then(iab -> {
			Preconditions.checkNotNull(iab);

			ab = iab; // NOTE Apparently not watching that hard
			env = ab.env();

			env.set(new types.MalSymbol("add-pipeline"), new _AddPipeline__MAL(compilationEnclosure));
		});

	}

	@Override
	public void postProcess() {
	}

	@Override
	public void prepare() throws Exception {
		env.re("(def! EvaPipeline 'native)");

		env.re("(add-pipeline 'LawabidingcitizenPipeline)");
		env.re("(add-pipeline 'EvaPipeline)");

		env.re("(add-pipeline 'DeducePipeline)"); // FIXME note moved from ...

		env.re("(add-pipeline 'WritePipeline)");
		// env.re("(add-pipeline 'WriteMesonPipeline)");
		env.re("(add-pipeline 'WriteMakefilePipeline)");
		env.re("(add-pipeline 'WriteOutputTreePipeline)"); // TODO add error checking
	}

	@Override
	public Operation<Ok> run(Compilation0 aComp, RP_Context ctx0) {
		final RP_Context_1 ctx = (RP_Context_1) ctx0;
		
		return run_((Compilation)aComp, ctx.getState(), ctx.getContext());
	}

	public Operation<Ok> run_(final @NotNull Compilation aCompilation, final CR_State st, final CB_Output output) {
		final GPipeline gPipeline = aCompilation.getCompilationEnclosure().getCompilationAccess().internal_pipelines();
		final Pipeline  ps        = (Pipeline) gPipeline;

		try {
			ps.run(st, output);
			return Operation.success(Ok.instance());
		} catch (Exception ex) {
			//Logger.getLogger(OStageProcess.class.getName()).log(Level.SEVERE, null, ex);
			//ex.printStackTrace();
			//throw new RuntimeException(ex);
			return Operation.failure(ex);
		}
	}
}
