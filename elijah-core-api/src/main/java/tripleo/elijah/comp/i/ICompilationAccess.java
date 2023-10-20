package tripleo.elijah.comp.i;

import tripleo.elijah.comp.*;
import tripleo.elijah.stages.logging.*;

import java.util.*;

public interface ICompilationAccess {
	void addFunctionMapHook(GFunctionMapHook aFunctionMapHook);

	List<GFunctionMapHook> functionMapHooks();

	void addPipeline(final GPipelineMember pl);

	Compilation0 getCompilation();

	GStages getStage();

	GPipeline internal_pipelines();

	void setPipelineLogic(final GPipelineLogic pl);

	ElLog.Verbosity testSilence();

	void writeLogs();
}
