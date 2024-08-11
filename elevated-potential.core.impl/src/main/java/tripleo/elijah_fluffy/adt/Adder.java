package tripleo.elijah_fluffy.adt;

import java.util.function.*;

public interface Adder<T> extends Consumer<T> {
	void add(T x);

	@Override
	default void accept(T x) {
		add(x);
	}
}
