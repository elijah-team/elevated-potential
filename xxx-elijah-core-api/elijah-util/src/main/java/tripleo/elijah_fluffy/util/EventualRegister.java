package tripleo.elijah_fluffy.util;

public interface EventualRegister {
	void checkFinishEventuals(); // TODO signature

	<P> void register(Eventual<P> e);
}
