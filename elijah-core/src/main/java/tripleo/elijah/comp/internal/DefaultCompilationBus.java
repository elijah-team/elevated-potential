package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;
import org.slf4j.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.util.*;

import java.util.*;
import java.util.concurrent.*;

import static tripleo.elijah.util.Helpers.*;

public class DefaultCompilationBus implements ICompilationBus {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultCompilationBus.class);
	private final CB_Monitor _monitor;
	@lombok.Getter
	private final @NotNull CompilerDriver compilerDriver;
	private final @NotNull Compilation c;
	//	private final @NotNull List<CB_Process> _processes = new ArrayList<>();
	@SuppressWarnings("TypeMayBeWeakened")
	private final Queue<CB_Process> pq = new ConcurrentLinkedQueue<>();

	private final @NotNull IProgressSink _defaultProgressSink = new IProgressSink() {
		@Override
		public void note(final Codes aCode, final @NotNull ProgressSinkComponent aProgressSinkComponent,
		                 final int aType, final Object[] aParams) {
			Stupidity.println_err_2(aProgressSinkComponent.printErr(aCode, aType, aParams));
		}
	};

	public DefaultCompilationBus(final @NotNull CompilationEnclosure ace) {
		c = ace.getCompilationAccess().getCompilation();
		compilerDriver = new CompilerDriver(this);

		ace.setCompilerDriver(compilerDriver);
		_monitor = new CompilationRunner.__CompRunner_Monitor();
	}

	@Override
	public IProgressSink defaultProgressSink() {
		return _defaultProgressSink;
	}

	@Override
	public CB_Monitor getMonitor() {
		return _monitor;
	}

	@Override
	public void add(final @NotNull CB_Action action) {
		pq.add(new SingleActionProcess(action, "CB_FindStdLibProcess"));
	}

	@Override
	public void add(final @NotNull CB_Process aProcess) {
		pq.add(aProcess);
	}

	@Override
	public void inst(final @NotNull ILazyCompilerInstructions aLazyCompilerInstructions) {
		_defaultProgressSink.note(
				IProgressSink.Codes.LazyCompilerInstructions_inst,
				ProgressSinkComponent.CompilationBus_,
				-1,
				new Object[] { aLazyCompilerInstructions.get() }
		);
	}

	@Override
	public void option(final @NotNull CompilationChange aChange) {
		aChange.apply(c);
	}

	@Override
	public List<CB_Process> processes() {
		return pq.stream().toList();//_processes;
	}

	public void runProcesses() {
		var procs = pq;

		final Thread thread = new Thread(() -> {
			LOG.debug("Polling...");
			boolean x = true;
			while (x) {
				final CB_Process poll = procs.poll();
				LOG.debug("Polled: " + poll);

				if (poll != null) {
					System.err.println("5757 "+ poll.name());
					poll.execute(this);
				} else {
					System.err.println("5758 poll returned null");
					LOG.debug("poll returned null");
					x = false;
				}
			}
		});
		thread.start();

//		for (final CB_Process process : pq) {
//			System.err.println("5757 "+process.name());
//			process.execute(this);
//		}

		try {
			thread.join();//TimeUnit.MINUTES.toMillis(1));
			thread.stop();
		} catch (InterruptedException aE) {
			throw new RuntimeException(aE);
		}
	}

}
