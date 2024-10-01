package tripleo.elijah_elevated_durable.pipelines.write;

import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.util.*;

public interface WP_Individual_Step extends CK_Action {
	void act(final WritePipelineSharedState st, final WP_State_Control sc);

	@Override
	default Operation<Ok> execute(CK_StepsContext context, CK_Monitor aMonitor) {
		// TODO maybe not here
		context.begin();

		var x = (WP_Flow) context;

		var st = x.context_st();
		var sc = x.context_sc();

		sc.cur(this, st, x.context_ops());

		return context.getExecutionResult();
	}
}
