package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.impl.CC_SetSilent;
import tripleo.elijah.comp.internal_move_soon.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.*;

import static tripleo.elijah.util.Helpers.*;

public class DefaultCompilationBus implements ICompilationBus {
	public static final int DEFUALT_COMPILATION_BUS__RUN_PROCESS__EXECUTE_LOG = 5757;
	private final CB_Monitor _monitor;
	@lombok.Getter
	private final @NotNull CompilerDriver    compilerDriver;
	private final @NotNull Compilation       c;
	//	private final @NotNull List<CB_Process> _processes = new ArrayList<>();
	//@SuppressWarnings("TypeMayBeWeakened")
	private final          Queue<CB_Process> pq = new ConcurrentLinkedQueue<>();

	private final @NotNull IProgressSink _defaultProgressSink;
//	new IProgressSink() {
//		@Override
//		public void note(final Codes aCode, final @NotNull ProgressSinkComponent aProgressSinkComponent,
//		                 final int aType, final Object[] aParams) {
//			Stupidity.println_err_2(aProgressSinkComponent.printErr(aCode, aType, aParams));
//		}
//	};

	public DefaultCompilationBus(final @NotNull CompilationEnclosure ace) {
		c                    = (@NotNull Compilation) ace.getCompilationAccess().getCompilation();
		_monitor             = new CompilationRunner.__CompRunner_Monitor();
		_defaultProgressSink = new DefaultProgressSink();

		compilerDriver = new CompilerDriver__(this);
		ace.setCompilerDriver(compilerDriver);
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
				new Object[]{aLazyCompilerInstructions.get()});
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
		final Queue<CB_Process> procs       = pq;
		final Startable         task        = this.c.con().askConcurrent(() -> __run_all_thread(procs), "[DefaultCompilationBus]");
		task.start();

		try {
			// TODO 10/20 Remove this soon
			final Thread thread = task.stealThread();

			// FIXME 23/01/04 awaitlity
			//await()
			thread.join();//TimeUnit.MINUTES.toMillis(1));

			for (final CB_Process process : pq) {
				logProgess(DEFUALT_COMPILATION_BUS__RUN_PROCESS__EXECUTE_LOG, process.name());
				execute_process(this, process);
			}

			thread.stop();
		} catch (InterruptedException aE) {
			throw new RuntimeException(aE);
		}
	}

	private void logProgess(final int code, final String message) {
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4(""+code+" "+message);
	}

	private void execute_process(final DefaultCompilationBus ignoredADefaultCompilationBus, final CB_Process aProcess) {
		//CompilationUnitTree
		//Compilation.Cheat.executeCB_Action(aProcess);
		if (alreadyP.contains(aProcess)) throw new Error();
		alreadyP.add(aProcess);
	}

	List<CB_Process> alreadyP = new ArrayList<>();

	private void __run_all_thread(final Queue<CB_Process> procs) {
		// FIXME passing sh*t between threads (P.O.!)
		_defaultProgressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, 5784, new Object[]{});
		long x = 0;
		while (x < 12) {
			final CB_Process poll = procs.poll();

			if (poll != null) {
				_defaultProgressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, DEFUALT_COMPILATION_BUS__RUN_PROCESS__EXECUTE_LOG, new Object[]{poll.name()});
				poll.execute(this);
			} else {
				_defaultProgressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, 5758, new Object[]{poll});
				try {
					Thread.sleep(500);
//					x = 0; // who put this here?
				} catch (InterruptedException aE) {
					//throw new RuntimeException(aE);
				}
			}
			++x;
		}
		_defaultProgressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, 5789, new Object[]{});
	}

	static class SingleActionProcess implements CB_Process {
		// README tape
		private final CB_Action a;
		private final String    name;

		public SingleActionProcess(final CB_Action aAction, final String aCBFindStdLibProcess) {
			a    = aAction;
			name = aCBFindStdLibProcess;
		}

		@Override
		public @NotNull List<CB_Action> steps() {
			return List_of(a);
		}

		@Override
		public String name() {
			return name;//"SingleActionProcess";
		}

	}

	@Override // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
	public void addCompilerChange(Class<?> class1) {
		if (class1.isInstance(CompilationChange.class)) {
			try {
				final CompilationChange compilationChange = (CompilationChange) class1.getDeclaredConstructor(new Class[]{}).newInstance();
				c.getCompilationEnclosure().getCompilationBus().option(compilationChange);
			} catch (InstantiationException | IllegalAccessException |InvocationTargetException | NoSuchMethodException e) {
				throw new Error();
			}
		}
	}
}
