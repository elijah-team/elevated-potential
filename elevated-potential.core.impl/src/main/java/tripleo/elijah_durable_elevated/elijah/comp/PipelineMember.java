package tripleo.elijah_durable_elevated.elijah.comp;

import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.g.GPipelineMember;
import tripleo.elijah.util.Ok;

public abstract class PipelineMember implements GPipelineMember {
	public abstract void run(Ok aSt, CB_Output aOutput) throws Exception;

	public abstract String finishPipeline_asString();
}
