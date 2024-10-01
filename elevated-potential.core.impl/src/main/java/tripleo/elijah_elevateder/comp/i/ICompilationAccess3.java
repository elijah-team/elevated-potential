package tripleo.elijah_elevateder.comp.i;

import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah_elevated_durable.pipelines.PipelineLogic;
import tripleo.elijah_elevateder.comp.i.Compilation;

import java.util.List;

public interface ICompilationAccess3 {
	Compilation getComp();

	boolean getSilent();

	void addLog(ElLog aLog);

	void writeLogs(boolean aSilent);

	PipelineLogic getPipelineLogic();

	List<ElLog> getLogs();
}
