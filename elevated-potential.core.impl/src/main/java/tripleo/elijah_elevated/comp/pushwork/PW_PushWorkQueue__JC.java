package tripleo.elijah_elevated.comp.pushwork;

import org.jctools.queues.MpscArrayQueue;
import tripleo.elijah.comp.nextgen.pw.PW_PushWork;
import tripleo.elijah.comp.nextgen.pw.PW_PushWorkQueue;

public class PW_PushWorkQueue__JC implements PW_PushWorkQueue {
	
	org.jctools.queues.MpscArrayQueue<PW_PushWork> q = new MpscArrayQueue<>(20);

	@Override
	public PW_PushWork poll() {
		return (PW_PushWork) q.poll();
	}

	@Override
	public void add(PW_PushWork aInstance) {
		q.failFastOffer(aInstance);
	}

}
