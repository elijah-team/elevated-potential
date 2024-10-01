package tripleo.elijah_elevateder.stages.gen_generic;

import org.jetbrains.annotations.Contract;
import tripleo.elijah_elevated_durable.pipelines.PipelineLogic;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah_elevateder.stages.logging.ElLog_;
import tripleo.elijah_elevateder.world.i.WorldModule;

public class OutputFileFactoryParams {
	private final CompilationEnclosure compilationEnclosure;
	private final WorldModule mod;

	@Contract(pure = true)
	public OutputFileFactoryParams(final WorldModule aMod, final CompilationEnclosure aCompilationEnclsure) {
		mod = aMod;

		// if (mod.ce != null) //!!

		compilationEnclosure = aCompilationEnclsure;
	}

	public CompilationEnclosure getCompilationEnclosure() {
		return compilationEnclosure;
	}

	public ErrSink getErrSink() {
		return compilationEnclosure.getCompilationClosure().errSink();
	}

	public OS_Module getMod() {
		return mod.module();
	}

	public PipelineLogic getPipelineLogic() {
		return getCompilationEnclosure().getPipelineLogic();
	}

	public ElLog_.Verbosity getVerbosity() {
		return compilationEnclosure.getCompilationAccess().testSilence();
	}

	public WorldModule getWorldMod() {
		return mod;
	}
}
