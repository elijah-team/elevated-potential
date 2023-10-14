package tripleo.elijah.stages.gen_generic;

import org.jetbrains.annotations.Contract;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.world.i.WorldModule;

public class OutputFileFactoryParams {
	private final CompilationEnclosure compilationEnclosure;
	private final WorldModule          mod;

	@Contract(pure = true)
	public OutputFileFactoryParams(final WorldModule aMod,
								   final CompilationEnclosure aCompilationEnclsure) {
		mod = aMod;

		//if (mod.ce != null) //!!

		compilationEnclosure = aCompilationEnclsure;
	}

	public OS_Module getMod() {
		return mod.module();
	}

	public WorldModule getWorldMod() {
		return mod;
	}

	public CompilationEnclosure getCompilationEnclosure() {
		return compilationEnclosure;
	}

	public PipelineLogic getPipelineLogic() {
		return getCompilationEnclosure().getPipelineLogic();
	}

	public ErrSink getErrSink() {
		return compilationEnclosure.getCompilationClosure().errSink();
	}

	public ElLog.Verbosity getVerbosity() {
		return compilationEnclosure.getCompilationAccess().testSilence();
	}
}
