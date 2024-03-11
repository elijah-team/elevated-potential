package tripleo.elijah_durable_elevated.elijah.comp;

import tripleo.elijah.stages.logging.ElLog;

import java.util.List;

public interface ICompilationAccess3 {
	Compilation getComp();

	boolean getSilent();

	void addLog(ElLog aLog);

	void writeLogs(boolean aSilent);

	PipelineLogic getPipelineLogic();

	List<ElLog> getLogs();
}
