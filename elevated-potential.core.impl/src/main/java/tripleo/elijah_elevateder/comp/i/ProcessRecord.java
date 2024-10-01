package tripleo.elijah_elevateder.comp.i;

import tripleo.elijah_elevated_durable.pipelines.PipelineLogic;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah_elevateder.comp.i.extra.IPipelineAccess;
import tripleo.elijah.g.GProcessRecord;

public interface ProcessRecord extends GProcessRecord {
	ICompilationAccess ca();

	IPipelineAccess pa();

	PipelineLogic pipelineLogic();

	@Override
	void writeLogs();
}