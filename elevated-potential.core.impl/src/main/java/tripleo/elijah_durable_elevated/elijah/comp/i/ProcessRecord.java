package tripleo.elijah_durable_elevated.elijah.comp.i;

import tripleo.elijah_durable_elevated.elijah.comp.PipelineLogic;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah_durable_elevated.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah.g.GProcessRecord;

public interface ProcessRecord extends GProcessRecord {
	ICompilationAccess ca();

	IPipelineAccess pa();

	PipelineLogic pipelineLogic();

	@Override
	void writeLogs();
}