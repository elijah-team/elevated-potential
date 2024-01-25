package tripleo.elijah.comp.internal;

import lombok.Getter;
import org.awaitility.core.ConditionTimeoutException;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.*;

import static org.awaitility.Awaitility.await;
import static tripleo.elijah.util.Helpers.List_of;

public class DefaultCompilationBus implements ICompilationBus {
	private final CB_Monitor _monitor;
	@Getter
	private final @NotNull CompilerDriver    _compilerDriver;
	private final @NotNull IProgressSink     _defaultProgressSink;
	private final @NotNull Compilation       c;
	private final @NotNull Queue<CB_Process> pq;
	private final @NotNull List<CB_Process>  alreadyP;

	public DefaultCompilationBus(final @NotNull CompilationEnclosure ace) {
		c                    = (@NotNull Compilation) ace.getCompilationAccess().getCompilation();
		pq                   = new ConcurrentLinkedQueue<>();
		alreadyP             = new ArrayList<>();
		_monitor             = new CompilationRunner.__CompRunner_Monitor();
		_defaultProgressSink = new DefaultProgressSink();
		_compilerDriver      = new CompilerDriver__(this);
		ace.setCompilerDriver(_compilerDriver);
	}

	@Override
	public void add(final @NotNull CB_Action action) {
		pq.add(new SingleActionProcess(action, "CB_FindStdLibProcess"));
	}

//	@Override public void addCompilerChange(Class<?> compilationChangeClass) {
//		if (compilationChangeClass.isInstance(CC_SetSilent.class)) {
//			try {
//				CompilationChange ccc = (CompilationChange) compilationChangeClass.getDeclaredConstructor(null).newInstance(null);
//				ccc.apply(this.c);
//			} catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
//				throw new RuntimeException(e);
//			}
//		}
//	}

	@Override
	public void add(final @NotNull CB_Process aProcess) {
		pq.add(aProcess);
	}

	@Override
	public IProgressSink defaultProgressSink() {
		return _defaultProgressSink;
	}

	@Override
	public CompilerDriver getCompilerDriver() {
		// 24/01/04 back and forth
		return this._compilerDriver;
	}

	@Override
	public void inst(final @NotNull ILazyCompilerInstructions aLazyCompilerInstructions) {
		_defaultProgressSink.note(IProgressSink.Codes.LazyCompilerInstructions_inst, ProgressSinkComponent.CompilationBus_, -1, new Object[]{aLazyCompilerInstructions.get()});
	}

	@Override
	public void option(final @NotNull CompilationChange aChange) {
		aChange.apply(c);
	}

	@Override
	public List<CB_Process> processes() {
		return pq.stream().toList();//_processes;
	}

	@Override
	public CB_Monitor getMonitor() {
		return _monitor;
	}

	@Override // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
	public void addCompilerChange(Class<?> class1) {
		if (class1.isInstance(CompilationChange.class)) {
			try {
				final CompilationChange compilationChange = (CompilationChange) class1.getDeclaredConstructor(new Class[]{}).newInstance();
				c.getCompilationEnclosure().getCompilationBus().option(compilationChange);
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException |
					 NoSuchMethodException e) {
				throw new Error();
			}
		}
	}

	public void runProcesses() {
		final Queue<CB_Process> procs = pq;

		final var xxx = this;
		var s = new CompFactory.StartableI() {
			@Override
			public void run() {
				// FIXME passing sh*t between threads (P.O.!)
				_defaultProgressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, 5784, new Object[]{});
				long x = 0;
				while (x < 12) {
					final CB_Process poll = procs.poll();

					if (poll != null) {
						_defaultProgressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, INTEGER_MARKER_CODES.DEFAULT_COMPILATION_BUS__RUN_PROCESS__EXECUTE_LOG, new Object[]{poll.name()});
						poll.execute(xxx);
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

			@Override
			public boolean isSignalled() {
				return false;
			}

			@Override
			public String getThreadName() {
				return "[DefaultCompilationBus]";
			}
		};

		final Startable         task  = this.c.con().askConcurrent(s);
		task.start();

		try {
			await()
					.atMost(2, TimeUnit.SECONDS)
					.until(() -> task.isSignalled()||c.get_pw().isSignalled());

			for (final CB_Process process : pq) {
				logProgess(INTEGER_MARKER_CODES.DEFAULT_COMPILATION_BUS__RUN_PROCESS__EXECUTE_LOG, process.name());
				execute_process(this, process);
			}
		} catch (ConditionTimeoutException timeout) {
			//throw new RuntimeException(aE);
		}
	}

	private void logProgess(final int code, final String message) {
		SimplePrintLoggerToRemoveSoon.println_out_4("" + code + " " + message);
	}

	private void execute_process(final DefaultCompilationBus ignoredADefaultCompilationBus, final CB_Process aProcess) {
		//CompilationUnitTree
		//Compilation.Cheat.executeCB_Action(aProcess);
		if (alreadyP.contains(aProcess)) throw new Error();
		alreadyP.add(aProcess);
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
}
