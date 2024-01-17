package tripleo.elijah.comp.internal;

public interface Startable {
	void start();

	Thread stealThread();

	boolean isSignalled();
}
