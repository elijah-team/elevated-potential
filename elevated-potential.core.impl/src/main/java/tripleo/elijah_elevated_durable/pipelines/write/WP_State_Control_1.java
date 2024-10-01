package tripleo.elijah_elevated_durable.pipelines.write;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.nextgen.pn.*;
import tripleo.elijah.nextgen.pn.*;
import tripleo.elijah.util.*;

/**
 * Purpose: to hold an exception for each {@link WP_Individual_Step}
 */
public class WP_State_Control_1 implements WP_State_Control {
	private @Nullable Exception          e;
	private @Nullable WP_Individual_Step __cur;
	private @Nullable WP_Flow.OPS        __cuo;

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
		if (__cur != null) {
			var step = __cur;
			var ops  = __cuo;
			ops.put(step, WP_Flow.FlowStatus.TRIED, Operation.success(Ok.instance()));
		}

		// FIXME 10/19 just mark for now
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("[%s] markSuccess (%s) (%s)".formatted("Default", this.getClass().getName(), aSuc.asString()));
		//NotImplementedException.raise_stop();
	}

	@Override
	public void markFailure(final SC_Fai aFai) {
		if (__cur != null) {
			var step = __cur;
			var ops  = __cuo;
			ops.put(step, WP_Flow.FlowStatus.FAILED, Operation.failure(this.getException()));
		}

		// FIXME 10/19 just mark for now
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("[%s] markSuccess (%s) (%s)".formatted("Default", this.getClass().getName(), aFai.sc_fai_asString()));
		//NotImplementedException.raise_stop();
	}

	@Override
	public void cur(final WP_Individual_Step step,
					final WritePipelineSharedState aWritePipelineSharedState,
					final WP_Flow.OPS ops) {
		// FIXME 10/19 crossover in ops
		this.clear();

		try {
			__cur = step;
			__cuo = ops;
			step.act(aWritePipelineSharedState, this);
		} finally {
			__cur = null;
			__cuo = null;
		}
	}
}
