package tripleo.elijah.stages.write_stage.pipeline_impl;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.nextgen.pn.SC_Suc;
import tripleo.elijah.nextgen.pn.SC_Fai;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

/**
 * Purpose: to hold an exception for each {@link WP_Individual_Step}
 */
public class WP_State_Control_1 implements WP_State_Control {
	private @Nullable Exception e;

	@Override
	public void clear() {
		e = null;
	}

	@Override
	public void exception(final Exception ee) {
		e = ee;
	}

	// TODO DiagnosticException == ExceptionDiagnostic
	@Override
	public Exception getException() {
		return e;
	}

	@Override
	public boolean hasException() {
		return e != null;
	}

	@Override
	public void markSuccess(final SC_Suc aSuc) {
		// FIXME 10/19 just mark for now
		System.err.println("[%s] markSuccess (%s) (%s)".formatted("Default", this.getClass().getName(), aSuc.asString()));
		//NotImplementedException.raise_stop();
	}

	@Override
	public void markFailure(final SC_Fai aFai) {
		// FIXME 10/19 just mark for now
		System.err.println("[%s] markSuccess (%s) (%s)".formatted("Default", this.getClass().getName(), aFai.sc_fai_asString()));
		//NotImplementedException.raise_stop();
	}

	@Override
	public void cur(final WP_Individual_Step step, final WritePipelineSharedState aWritePipelineSharedState, final WP_Flow.OPS ops) {

		// FIXME 10/19 crossover in ops

		this.clear();

		step.act(aWritePipelineSharedState, this);

		if (!this.hasException()) {
			ops.put(step, WP_Flow.FlowStatus.TRIED, Operation.success(Ok.instance()));
		} else {
			ops.put(step, WP_Flow.FlowStatus.FAILED, Operation.failure(this.getException()));
		}
	}
}
