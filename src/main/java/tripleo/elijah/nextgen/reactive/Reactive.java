package tripleo.elijah.nextgen.reactive;

import java.util.function.Consumer;

/**
 * The base element of <b>reactive</b>.
 * 
 * Derive from this and add to {@code CompilationEnclosure#addReactive}
 * 
 * Wastefully, {@link #join(ReactiveDimension)} will be called for all
 * {@link Reactives} times all {@link ReactiveDimension}s
 */
public interface Reactive {
	void add(Reactivable aReactivable);

	<T> void addListener(Consumer<T> t);

	void join(ReactiveDimension aDimension);
}
