package tripleo.elijah_durable_elevated.elijah.stages.write_stage.pipeline_impl;

import tripleo.elijah.comp.nextgen.pn.SC_Suc;
import tripleo.elijah.nextgen.pn.SC_Fai;

public interface WP_State_Control {
	void clear();

	void exception(final Exception e);

	Exception getException();

	boolean hasException();

	void markSuccess(SC_Suc aSuc);

	void markFailure(SC_Fai aFai);

	void cur(WP_Individual_Step step, WritePipelineSharedState aWritePipelineSharedState, final WP_Flow.OPS aOps);

	//void cur(BiConsumer aBiConsumer);
}
