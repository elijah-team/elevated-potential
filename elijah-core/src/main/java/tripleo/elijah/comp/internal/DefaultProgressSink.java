package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;

public class DefaultProgressSink implements IProgressSink {
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
