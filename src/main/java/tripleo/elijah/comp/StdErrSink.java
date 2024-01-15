package tripleo.elijah.comp;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tripleo(sb)
 */
public class StdErrSink implements ErrSink {
	private final List<Pair<Errors, Object>> _list = new ArrayList<>();
	private int _errorCount;

	@Override
	public int errorCount() {
		return _errorCount;
	}

	@Override
	public void exception(final @NotNull Exception e) {
		_errorCount++;
		SimplePrintLoggerToRemoveSoon.println_err_4("exception: " + e);
		e.printStackTrace(System.err);
		_list.add(Pair.of(Errors.EXCEPTION, e));
	}

	@Override
	public void info(final String message) {
		_list.add(Pair.of(Errors.INFO, message));
		System.err.printf("INFO: %s%n", message);
	}

	@Override
	public void reportDiagnostic(@NotNull Diagnostic diagnostic) {
		if (diagnostic.severity() == Diagnostic.Severity.ERROR)
			_errorCount++;
		_list.add(Pair.of(Errors.DIAGNOSTIC, diagnostic));
		// 08/13 diagnostic.report(System.err);
	}

	@Override
	public void reportError(final String s) {
		_errorCount++;
		_list.add(Pair.of(Errors.ERROR, s));
		System.err.printf("ERROR: %s%n", s);
	}

	@Override
	public void reportWarning(final String s) {
		_list.add(Pair.of(Errors.WARNING, s));
		System.err.printf("WARNING: %s%n", s);
	}

	@Override
	public List<Pair<Errors, Object>> list() {
		return _list;
	}
}
