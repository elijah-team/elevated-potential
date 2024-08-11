package tripleo.elijah.comp.internal;

import lombok.*;
import tripleo.elijah.comp.i.*;

public class CB_ForwardingOutput implements CB_Output {
	@Delegate
	private final CB_Output output;

	public CB_ForwardingOutput(final CB_Output aOutput) {
		output = aOutput;
	}

	//@Override
	// public @NotNull List<CB_OutputString> get() {
	//	return output.get();
	//}
	//
	//@Override
	//public void logProgress(final int number, final String text) {
	//	output.logProgress(number, text);
	//}
	//
	//@Override
	//public void print(final String s) {
	//	output.print(s);
	//}
	//
	//@Override
	//public void logProgress(final Diagnostic aDiagnostic) {
	//	output.logProgress(aDiagnostic);
	//}
}
