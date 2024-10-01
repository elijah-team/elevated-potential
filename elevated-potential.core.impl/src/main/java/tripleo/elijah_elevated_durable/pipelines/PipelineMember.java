package tripleo.elijah_elevated_durable.pipelines;

import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.g.GPipelineMember;
import tripleo.elijah_fluffy.util.Ok;

public abstract class PipelineMember implements GPipelineMember {
	public abstract void run(Ok aSt, CB_Output aOutput) throws Exception;

	public abstract String finishPipeline_asString();
}
