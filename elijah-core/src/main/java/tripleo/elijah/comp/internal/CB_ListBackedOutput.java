package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.*;
import tripleo.elijah.diagnostic.Diagnostic;

import java.util.ArrayList;
import java.util.List;

public class CB_ListBackedOutput implements CB_Output {
	private final List<CB_OutputString> x = new ArrayList<>();

	@Override
	public @NotNull List<CB_OutputString> get() {
		return x;
	}

	@Override
	public void logProgress(final int number, final String text) {
		if (number == 130)
			return;

//		System.err.println
		print(String.format("%d %s", number, text));
	}

	@Override
	public void print(final String s) {
		x.add(() -> s);
	}

	@Override
	public void logProgress(final Diagnostic aDiagnostic) {
		if (aDiagnostic instanceof CodedOperationDiagnostic<?> coded) {
			logProgress(coded.intCode(), coded.message());
		} else {
			// FIXME 10/20 dont worry about this yet
//			logProgress(aDiagnostic.code(), aDiagnostic.message());
		}
	}
}

//
//
//
