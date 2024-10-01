package tripleo.elijah_fluffy.util;

import tripleo.elijah_fluffy.diagnostic.Diagnostic;

public interface CompletableProcess<T> {
	void add(T item);

	void complete();

	void error(Diagnostic d);

	void preComplete();

	void start();
}
