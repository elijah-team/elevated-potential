package tripleo.elijah_elevateder.diagnostic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah_fluffy.diagnostic.Diagnostic;
import tripleo.elijah_fluffy.diagnostic.Locatable;
import tripleo.elijah_fluffy.util.Operation;

import java.io.PrintStream;
import java.util.List;

/**
 * No Location info !!!
 *
 * @param <T>
 */
public class CodedOperationDiagnostic<T> implements Diagnostic {
	private final int       code;
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
		return null;
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
