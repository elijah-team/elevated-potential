package tripleo.elijah.diagnostic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.diagnostic.Locatable;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.UnintendedUseException;

import java.io.PrintStream;
import java.util.List;

/**
 * No Location info !!!
 *
 * @param <T>
 */
public class CodedOperationDiagnostic<T> implements Diagnostic {
	private final int          code;
	private final String       message;
	private final Operation<T> operation;

	CodedOperationDiagnostic(final int aCode, final String aMessage, final Operation<T> aOperation) {
		code      = aCode;
		message   = aMessage;
		operation = aOperation;
	}

	@Override
	public @Nullable String code() {
		return ""+this.code;
	}

	@Override
	public @NotNull Locatable primary() {
		throw new UnintendedUseException("impl me ltr");
	}

	@Override
	public void report(final PrintStream stream) {
		stream.println("%s %s".formatted(code(), this.message()));
	}

	public String message() {
		switch (operation.mode()) {
		case SUCCESS -> {
			return "SUCCESS: " + this.message;
		}
		case FAILURE -> {
			// TODO 10/20 stacktrace??
			return "FAILURE: %s %s".formatted(this.message, operation.failure().toString());
		}
		default -> throw new IllegalStateException("Unexpected value: " + operation.mode());
		}
	}

	@Override
	public @NotNull List<Locatable> secondary() {
		return null;
	}

	@Override
	public @Nullable Severity severity() {
		switch (operation.mode()) {
		case FAILURE -> {
			return Severity.ERROR;
		}
		case NOTHING -> {
			return Severity.WARN;
		}
		case SUCCESS -> {
			return Severity.INFO;
		}
		default -> throw new IllegalStateException("Unexpected value: " + operation.mode());
		}
	}

	public int intCode() {
		return this.code;
	}
}
