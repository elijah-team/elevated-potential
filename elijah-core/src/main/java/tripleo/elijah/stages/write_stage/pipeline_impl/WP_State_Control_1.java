package tripleo.elijah.stages.write_stage.pipeline_impl;

import org.jetbrains.annotations.Nullable;

/**
 * Purpose: to hold an exception for each {@link WP_Indiviual_Step}
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
}
