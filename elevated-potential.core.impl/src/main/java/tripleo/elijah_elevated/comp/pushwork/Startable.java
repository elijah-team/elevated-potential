package tripleo.elijah_elevated.comp.pushwork;

public interface Startable {
	void start();

	Thread stealThread();

	boolean isSignalled();
}
