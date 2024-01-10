package tripleo.elijah.comp;

import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.comp.internal.CR_State;

public abstract class PipelineMember {
	public abstract void run(CR_State aSt, CB_Output aOutput) throws Exception;

	public abstract String finishPipeline_asString();
}
