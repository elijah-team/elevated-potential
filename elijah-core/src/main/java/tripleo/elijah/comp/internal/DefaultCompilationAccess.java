package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.notation.*;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.logging.*;

import java.util.*;

public class DefaultCompilationAccess implements ICompilationAccess {
	protected final Compilation compilation;
	private final   Pipeline    pipelines = new Pipeline();

	@Contract(pure = true)
	public DefaultCompilationAccess(final Compilation aCompilation) {
		compilation = aCompilation;
	}

	@Override
	public void addFunctionMapHook(final IFunctionMapHook aFunctionMapHook) {
		compilation.getCompilationEnclosure().getPipelineLogic().dp.addFunctionMapHook(aFunctionMapHook);
	}

	@Override
	public void addPipeline(final PipelineMember pl) {
		pipelines.add(pl);
	}

	@Override
	public @NotNull List<IFunctionMapHook> functionMapHooks() {
		return compilation.getCompilationEnclosure().getPipelineLogic().dp.functionMapHooks;
	}

	@Override
	public Compilation getCompilation() {
		return compilation;
	}

	@Override
	public @NotNull Stages getStage() {
		return Stages.O;
	}

	@Override
	public @NotNull Pipeline internal_pipelines() {
		return pipelines;
	}

	@Override
	public void setPipelineLogic(final PipelineLogic pl) {
		assert compilation.getCompilationEnclosure().getPipelineLogic() == null;
		compilation.getCompilationEnclosure().setPipelineLogic(pl);
	}

	@Override
	@NotNull
	public ElLog.Verbosity testSilence() {
		return compilation.cfg().silent ? ElLog.Verbosity.SILENT : ElLog.Verbosity.VERBOSE;
	}

	@Override
	public void writeLogs() {
		final CompilationEnclosure ce            = compilation.getCompilationEnclosure();
		final PipelineLogic        pipelineLogic = ce.getPipelineLogic();
		final IPipelineAccess      pa            = compilation.pa();

		pa.notate(Provenance.DefaultCompilationAccess__writeLogs, new GN_WriteLogs(this, pipelineLogic.getLogs()));
	}
}
