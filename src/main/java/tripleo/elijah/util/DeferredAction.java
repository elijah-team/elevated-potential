package tripleo.elijah.util;

import tripleo.elijah.util2.Eventual;

public interface DeferredAction<T> {
	String description();

	boolean completed();

	Eventual<T> promise();

	void calculate();
}
