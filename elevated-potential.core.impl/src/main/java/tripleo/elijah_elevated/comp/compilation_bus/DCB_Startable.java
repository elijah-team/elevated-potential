package tripleo.elijah_elevated.comp.compilation_bus;

import tripleo.elijah.comp.i.*;
import tripleo.elijah_elevateder.comp.i.CompFactory;
import tripleo.elijah_elevateder.comp.internal.INTEGER_MARKER_CODES;

import java.util.Queue;

public class DCB_Startable implements CompFactory.StartableI {
	public static final int DefaultCompilationBus_POLL_PROCESS             = 5784;
	public static final int DefaultCompilationBus_POLL_PROCESS_EMPTY_BEGIN = 5758;
	public static final int DefaultCompilationBus_POLL_PROCESS_EMPTY_END   = 5789;

	private final ICompilationBus   compilationBus;
	private final Queue<CB_Process> processQueue;
	private final IProgressSink     progressSink;

	public DCB_Startable(final ICompilationBus aCompilationBus, final Queue<CB_Process> aProcessQueue, IProgressSink aProgressSink) {
		compilationBus = aCompilationBus;
		processQueue   = aProcessQueue;
		progressSink   = aProgressSink;
	}

	@Override
	public void run() {
		// FIXME passing sh*t between threads (P.O.!)
		logProgress(DefaultCompilationBus_POLL_PROCESS, new Object[]{});
		long x = 0;
		while (x < 12) {
			final CB_Process poll = processQueue.poll();

			if (poll != null) {
				logProgress(INTEGER_MARKER_CODES.DEFAULT_COMPILATION_BUS__RUN_PROCESS__EXECUTE_LOG, new Object[]{poll.name()});
				poll.execute(compilationBus);
			} else {
				logProgress(DefaultCompilationBus_POLL_PROCESS_EMPTY_BEGIN, new Object[]{poll});
				try {
					Thread.sleep(500);
//					x = 0; // who put this here?
				} catch (InterruptedException aE) {
					//throw new RuntimeException(aE);
				}
			}
			++x;
		}
		logProgress(DefaultCompilationBus_POLL_PROCESS_EMPTY_END, new Object[]{});
	}

	@Override
	public boolean isSignalled() {
		return false;
	}

	@Override
	public String getThreadName() {
		return "[DefaultCompilationBus]";
	}

	void logProgress(final int a, final Object[] b) {
		progressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, a, b);
	}
}
