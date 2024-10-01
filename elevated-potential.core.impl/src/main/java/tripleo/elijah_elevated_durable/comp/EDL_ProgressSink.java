package tripleo.elijah_elevated_durable.comp;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.IProgressSink;
import tripleo.elijah.comp.i.ProgressSinkComponent;

public class EDL_ProgressSink implements IProgressSink {
	@Override
	public void note(final Codes code,
	                 final @NotNull ProgressSinkComponent component,
	                 final int type,
	                 final Object[] params) {
		// component.note(code, type, params);
		if (component.isPrintErr(code, type)) {
			final String s = component.printErr(code, type, params);
			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4(s);
		}
	}
}
