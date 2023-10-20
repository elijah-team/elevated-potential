package tripleo.elijah.comp.internal;

import tripleo.elijah.comp.nextgen.*;
import tripleo.elijah.comp.nextgen.pw.*;

import java.util.*;
import java.util.concurrent.*;

public class PW_CompilerController implements PW_Controller, Runnable {
	private final CompilationImpl compilation;

	PW_CompilerController(final CompilationImpl aC) {
		new Thread(this).run();
		compilation = aC;
	}

	private final Queue<PW_PushWork> wq = new LinkedBlockingQueue/*ConcurrentLinkedQueue*/<>();

	@Override
	public void run() {
		// FIXME 10/18 this is also a steps: A+O
////             FIXME passing sh*t between threads (P.O.!)
		//_defaultProgressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, 5784, new Object[]{});
		boolean x = true;
		while (x) {
			//final PW_PushWork poll = wq.poll();
			final PW_PushWork poll;
			try {
				poll = ((BlockingQueue<PW_PushWork>) wq).take();
			} catch (InterruptedException aE) {
				throw new RuntimeException(aE);
			}

			if (poll != null) {
//                _defaultProgressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, 5757, new Object[]{poll.name()});
				poll.execute(this);
			} else {
				//              _defaultProgressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, 5758, new Object[]{poll});







				// README 10/20 fails everything after one failed poll


//nfdskj;lfndskjlfngdsjklfndsjklfdsnjfkldsnfdsjklfndsjklfndsjfkldsnkls                x = false;
			}
		}
	}

	public void submitWork(final PW_PushWork aInstance) {
		wq.add(aInstance);
	}

	public CP_Paths paths() {
		return compilation._paths();
	}
}
