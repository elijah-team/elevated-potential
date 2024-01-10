package tripleo.elijah.util2;

public interface EventualRegister {
	void checkFinishEventuals(); // TODO signature

	<P> void register(Eventual<P> e);
}
