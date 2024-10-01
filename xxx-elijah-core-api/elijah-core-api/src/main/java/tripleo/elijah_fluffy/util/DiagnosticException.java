package tripleo.elijah_fluffy.util;

import tripleo.elijah_fluffy.diagnostic.Diagnostic;

public class DiagnosticException extends Throwable {
	private final Diagnostic d;

	public DiagnosticException(final Diagnostic aD) {
		d = aD;
	}
}
