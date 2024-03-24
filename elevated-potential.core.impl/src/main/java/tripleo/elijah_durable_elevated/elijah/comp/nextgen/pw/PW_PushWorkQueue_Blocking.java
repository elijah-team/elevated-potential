package tripleo.elijah_durable_elevated.elijah.comp.nextgen.pw;

import tripleo.elijah.comp.nextgen.pw.PW_PushWork;
import tripleo.elijah.comp.nextgen.pw.PW_PushWorkQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PW_PushWorkQueue_Blocking implements PW_PushWorkQueue {
	private final BlockingQueue<PW_PushWork> _wq = new LinkedBlockingQueue<PW_PushWork>();

	@Override
	public PW_PushWork poll() {
		final PW_PushWork poll;
		try {
			poll = _wq.take();
		} catch (InterruptedException aE) {
			throw new RuntimeException(aE);
		}
		return poll;
	}

	@Override
	public void add(final PW_PushWork aInstance) {
		_wq.add(aInstance);
	}
}
