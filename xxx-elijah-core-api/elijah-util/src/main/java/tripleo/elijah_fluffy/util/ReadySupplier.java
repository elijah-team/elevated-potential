package tripleo.elijah_fluffy.util;

import java.util.function.Supplier;

public interface ReadySupplier<T> extends Supplier<T> {
	@Override
	T get();

	boolean ready();
}