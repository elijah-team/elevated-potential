package tripleo.elijah.util;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.diagnostic.*;
import tripleo.elijah.nextgen.query.*;

import static tripleo.elijah.nextgen.query.Mode.*;

/**
 * An emulation of Rust's Result type
 *
 * @param <T> the success type
 */
public class Operation<T> /* extends Operation2<T> */ {
	public static <T> @NotNull Operation<T> failure(final Exception aException) {
		final Operation<T> op = new Operation<>(null, aException, FAILURE);
		return op;
	}

	public static <T> @NotNull Operation<T> success(final T aSuccess) {
		final Operation<T> op = new Operation<>(aSuccess, null, SUCCESS);
		return op;
	}

	private final Mode mode;

	private final T succ;

	private final Exception exc;

	public Operation(final T aSuccess, final Exception aException, final Mode aMode) {
		succ = aSuccess;
		exc = aException;
		mode = aMode;

		assert succ != exc;
	}

	public static Operation<CompilerInstructions> convert(final Operation2<CompilerInstructions> aOperation2) {
		switch (aOperation2.mode()) {
//		case FAILURE -> {
//
//			return
//					Operation.failure(null);//new ExceptionDiagnostic(aOperation2.failure()))
//		}
		case SUCCESS -> {
			return Operation.success(aOperation2.success());
		}
		default -> throw new IllegalStateException("Unexpected value: " + aOperation2.mode());
		}
	}

	public Exception failure() {
		return exc;
	}

	public @NotNull Mode mode() {
		return mode;
	}

	public T success() {
		return succ;
	}
}
