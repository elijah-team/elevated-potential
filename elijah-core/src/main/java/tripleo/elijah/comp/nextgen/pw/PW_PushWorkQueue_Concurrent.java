package tripleo.elijah.comp.nextgen.pw;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PW_PushWorkQueue_Concurrent implements PW_PushWorkQueue {
	private final Queue<PW_PushWork> _wq = new ConcurrentLinkedQueue<>();

	@Override
	public PW_PushWork poll() {
		final PW_PushWork poll = _wq.poll();
		return poll;
	}

	@Override
	public void add(final PW_PushWork aInstance) {
		_wq.add(aInstance);
	}
}
