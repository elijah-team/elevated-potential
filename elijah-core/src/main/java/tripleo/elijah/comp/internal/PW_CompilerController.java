package tripleo.elijah.comp.internal;

import tripleo.elijah.comp.i.IProgressSink;
import tripleo.elijah.comp.i.ProgressSinkComponent;
import tripleo.elijah.comp.nextgen.CP_Paths;
import tripleo.elijah.comp.nextgen.pw.PW_Controller;
import tripleo.elijah.comp.nextgen.pw.PW_PushWork;
import tripleo.elijah.comp.nextgen.pw.PW_PushWorkQueue;

public class PW_CompilerController implements PW_Controller, Runnable {
	private final CompilationImpl compilation;
	private final PW_PushWorkQueue wq;
	private final IProgressSink _defaultProgressSink;

	PW_CompilerController(final CompilationImpl aC) {
		compilation = aC;

		// TODO 10/20 Make a start latch, then overcomplicate (Lifetime erl etc)
		//  for now just return a "startable"
		Startable task = compilation.con().askConcurrent(this, "[PW_CompilerController]");

		wq = compilation.con().createWorkQueue();

		this._defaultProgressSink = compilation.getCompilationEnclosure().getCompilationRunner().getProgressSink();

		task.start();
	}

	@Override
	public void run() {
		// FIXME 10/18 this is also a steps: A+O
////             FIXME passing sh*t between threads (P.O.!)
		_defaultProgressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, 5784, new Object[]{});
		boolean x = true;
		while (x) {
			final PW_PushWork poll = wq.poll();

			if (poll != null) {
                _defaultProgressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, 5757, new Object[]{poll.name()});
				poll.execute(this);
			} else {
				              _defaultProgressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, 5758, new Object[]{poll});


				// README 10/20 fails everything after one failed poll


//nfdskj;lfndskjlfngdsjklfndsjklfdsnjfkldsnfdsjklfndsjklfndsjfkldsnkls
				x = false;
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

//
//
//
