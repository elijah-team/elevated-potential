package tripleo.elijah.comp.i;

import tripleo.elijah.comp.*;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.logging.*;

import java.util.*;

public interface ICompilationAccess {
	void addFunctionMapHook(IFunctionMapHook aFunctionMapHook);

	void addPipeline(final PipelineMember pl);

	List<IFunctionMapHook> functionMapHooks();

	Compilation getCompilation();

	Stages getStage();

	Pipeline internal_pipelines();

	void setPipelineLogic(final PipelineLogic pl);

	ElLog.Verbosity testSilence();

	void writeLogs();
}
