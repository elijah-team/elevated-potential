package tripleo.elijah.g;

import org.apache.commons.lang3.tuple.Pair;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.ICompilationRunner;
import tripleo.elijah.comp.nextgen.i.AsseverationLogProgress;
import tripleo.elijah.stages.logging.ElLog;

import java.util.List;

public interface GCompilationEnclosure {
	GModuleThing addModuleThing(GOS_Module aModule);

	void logProgress(CompProgress aCompProgress, Pair<Integer, String> aCodeMessagePair);

	GModuleThing getModuleThing(GOS_Module aModule);

	void logProgress2(CompProgress aCompProgress, AsseverationLogProgress aAsseverationLogProgress);

	CB_Output getCB_Output();

	ICompilationRunner getCompilationRunner();

	ICompilationBus getCompilationBus();

	void addLog(ElLog aLog);

	List<ElLog> getLogs();

	void writeLogs();

	GPipelineLogic getPipelineLogic();
}
