package tripleo.elijah.javac_model.tools;

import java.util.*;

public final class DiagnosticCollector<S> implements DiagnosticListener<S> {
	private final List<Diagnostic<? extends S>> diagnostics =
			Collections.synchronizedList(new ArrayList<Diagnostic<? extends S>>());

	public DiagnosticCollector() {
	}

	@Override
	public void report(Diagnostic<? extends S> diagnostic) {
		Objects.requireNonNull(diagnostic);
		diagnostics.add(diagnostic);
	}

	public List<Diagnostic<? extends S>> getDiagnostics() {
		return Collections.unmodifiableList(diagnostics);
	}
}
