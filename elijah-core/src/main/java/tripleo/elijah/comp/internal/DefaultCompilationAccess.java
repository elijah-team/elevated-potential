package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.*;
import tripleo.elijah.comp.internal_move_soon.*;
import tripleo.elijah.comp.notation.*;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.logging.*;

import java.util.*;

public class DefaultCompilationAccess implements ICompilationAccess {
	protected final Compilation compilation;
	private final   Pipeline     pipelines = new Pipeline();

	@Contract(pure = true)
	public DefaultCompilationAccess(final Compilation aCompilation) {
		compilation = aCompilation;
	}

	@Override
	public void addFunctionMapHook(final GFunctionMapHook aFunctionMapHook1) {
		IFunctionMapHook aFunctionMapHook = (IFunctionMapHook) aFunctionMapHook1;
		compilation.getCompilationEnclosure().getPipelineLogic().dp.addFunctionMapHook(aFunctionMapHook);
	}

	@Override
	public void addPipeline(final GPipelineMember pl) {
		pipelines.add((PipelineMember)pl);
	}

	@Override
	public @NotNull List<GFunctionMapHook> functionMapHooks() {
		return Collections.unmodifiableList(compilation.getCompilationEnclosure().getPipelineLogic().dp.functionMapHooks);
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
	public @NotNull GPipeline internal_pipelines() {
		return pipelines;
	}

	@Override
	public void setPipelineLogic(final GPipelineLogic pl) {
		setPipelineLogic((PipelineLogic) pl);
	}

	//@Override
	public void setPipelineLogic(final PipelineLogic pl) {
		//assert compilation.getCompilationEnclosure().getPipelineLogic() == null;
		if (compilation.getCompilationEnclosure().getPipelineLogic() != null) {
			//throw new AssertionError();
			System.err.println("903056 pipelineLogic already set");
		} else {
			compilation.getCompilationEnclosure().setPipelineLogic(pl);
		}
	}

	@Override
	@NotNull
	public ElLog_.Verbosity testSilence() {
		return compilation.cfg().silent ? ElLog_.Verbosity.SILENT : ElLog_.Verbosity.VERBOSE;
	}

	@Override
	public void writeLogs() {
		final CompilationEnclosure ce            = compilation.getCompilationEnclosure();
		final PipelineLogic   pipelineLogic = ce.getPipelineLogic();
		final IPipelineAccess pa            = compilation.pa();

		pa.notate(Provenance.DefaultCompilationAccess__writeLogs, new GN_WriteLogs(this, pipelineLogic.getLogs()));
	}
}
