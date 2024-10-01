package tripleo.elijah_fluffy.util;

public interface DeferredAction<T> {
	String description();

	boolean completed();

	Eventual<T> promise();

	void calculate();
}
