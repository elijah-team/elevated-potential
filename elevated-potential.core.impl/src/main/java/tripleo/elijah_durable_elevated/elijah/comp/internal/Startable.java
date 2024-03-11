package tripleo.elijah_durable_elevated.elijah.comp.internal;

public interface Startable {
	void start();

	Thread stealThread();

	boolean isSignalled();
}
