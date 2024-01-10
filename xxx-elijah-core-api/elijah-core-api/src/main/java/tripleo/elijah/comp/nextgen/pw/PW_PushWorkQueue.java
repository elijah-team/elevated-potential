package tripleo.elijah.comp.nextgen.pw;

public interface PW_PushWorkQueue {
	PW_PushWork poll();

	void add(PW_PushWork aInstance);
}
