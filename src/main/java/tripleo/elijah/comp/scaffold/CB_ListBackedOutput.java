package tripleo.elijah.comp.scaffold;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.comp.i.CB_OutputString;
import tripleo.elijah.diagnostic.CodedOperationDiagnostic;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.util.UnintendedUseException;

import java.util.*;

public class CB_ListBackedOutput implements CB_Output {
	private final List<CB_OutputString> _listBacking = new ArrayList<>();

	@Override
	public @NotNull List<CB_OutputString> get() {
		return _listBacking;
	}

	@Override
	public void logProgress(final int number, final String text) {
		if (number == 130)
			return;

//		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4
		print(String.format("%d %s", number, text));
	}

	@Override
	public void print(final String s) {
		_listBacking.add(() -> s);
	}

	@Override
	public void logProgress(final Diagnostic aDiagnostic) {
		if (aDiagnostic instanceof CodedOperationDiagnostic<?> coded) {
			logProgress(coded.intCode(), coded.message());
		} else {
			// FIXME 10/20 dont worry about this yet
//			logProgress(aDiagnostic.code(), aDiagnostic.message());
			throw new UnintendedUseException("see what this is");
		}
	}
}
