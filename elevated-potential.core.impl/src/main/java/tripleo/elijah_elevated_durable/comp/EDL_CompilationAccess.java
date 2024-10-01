package tripleo.elijah_elevated_durable.comp;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.g.*;
import tripleo.elijah_elevated_durable.pipelines.*;
import tripleo.elijah_elevateder.comp.Compilation;
import tripleo.elijah_elevateder.stages.deduce.IFunctionMapHook;
import tripleo.elijah_elevateder.stages.logging.ElLog_;
import tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon;

import java.util.Collections;
import java.util.List;

public class EDL_CompilationAccess implements ICompilationAccess {
	protected final Compilation compilation;
	private final   Pipeline    pipelines = new Pipeline();

	@Contract(pure = true)
	public EDL_CompilationAccess(final Compilation aCompilation) {
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
			SimplePrintLoggerToRemoveSoon.println_err_4("903056 pipelineLogic already set");
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
	public GCompilationEnclosure getCompilationEnclosure() {
		return getCompilation().getCompilationEnclosure();
	}

}
