package tripleo.elijah_fluffy.util;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_fluffy.diagnostic.Diagnostic;

/**
 * A workaround for the lack of Union Types
 *
 * @param <T> the success type
 */
public class Operation<T> {
	public static <T> @NotNull Operation<T> failure(final Throwable aThrowable) {
		final Operation<T> op = new Operation<>(null, aThrowable, Mode.FAILURE);
		return op;
	}

	public static <T> @NotNull Operation<T> success(final T aSuccess) {
		final Operation<T> op = new Operation<>(aSuccess, null, Mode.SUCCESS);
		return op;
	}

	private final Mode mode;

	private final T succ;

	private final Throwable exc;

	public Operation(final T aSuccess, final Throwable aException, final Mode aMode) {
		succ = aSuccess;
		exc = aException;
		mode = aMode;

		assert succ != exc;
	}

	public static <T> Operation<T> convert(final Operation2<T> aOperation2) {
		switch (aOperation2.mode()) {
		case FAILURE -> {
			final Diagnostic failure = aOperation2.failure();
			final DiagnosticException de = new DiagnosticException(failure);
			return Operation.failure(de);
		}
		case SUCCESS -> {
			return Operation.success(aOperation2.success());
		}
		default -> throw new IllegalStateException("Unexpected value: " + aOperation2.mode());
		}
	}

	public Throwable failure() {
		return exc;
	}

	public @NotNull Mode mode() {
		return mode;
	}

	public T success() {
		return succ;
	}
}
