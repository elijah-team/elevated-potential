package tripleo.elijah.comp.i;

import tripleo.elijah.comp.AccessBus;
import tripleo.elijah.comp.PipelineLogic;
import tripleo.elijah.comp.i.extra.*;

public interface ProcessRecord extends GProcessRecord {
	AccessBus ab();

	ICompilationAccess ca();

	IPipelineAccess pa();

	PipelineLogic pipelineLogic();

	@Override
	void writeLogs();
}