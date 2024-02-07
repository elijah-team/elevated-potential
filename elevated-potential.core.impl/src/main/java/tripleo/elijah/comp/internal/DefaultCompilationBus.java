package tripleo.elijah.comp.internal;

import lombok.Getter;
import org.awaitility.core.*;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static tripleo.elijah.util.Helpers.List_of;

public class DefaultCompilationBus implements ICompilationBus {
	private final CB_Monitor _monitor;
	@Getter
	private final @NotNull CompilerDriver    _compilerDriver;
	private final @NotNull IProgressSink     _defaultProgressSink;
	private final @NotNull Compilation       _c;
	private final @NotNull Queue<CB_Process> _pq;
	@Deprecated private final @NotNull List<CB_Process>  _alreadyP;

	public DefaultCompilationBus(final @NotNull CompilationEnclosure ace) {
		_alreadyP = new ArrayList<>();
		_pq       = new ConcurrentLinkedQueue<>();

		_c                   = (@NotNull Compilation) ace.getCompilationAccess().getCompilation();
		_defaultProgressSink = new DefaultProgressSink();

		_compilerDriver      = new CompilerDriver__(this);
		ace.setCompilerDriver(_compilerDriver);

		_monitor             = ace.getCompilerController()._instance().newMonitor();
	}

	@Override
	public void add(final @NotNull CB_Action action) {
		_pq.add(new SingleActionProcess(action, "CB_FindStdLibProcess"));
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
		_pq.add(aProcess);
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
		aChange.apply(_c);
	}

	@Override
	public List<CB_Process> processes() {
		return _pq.stream().toList();//_processes;
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
				_c.getCompilationEnclosure().getCompilationBus().option(compilationChange);
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException |
					 NoSuchMethodException e) {
				throw new Error();
			}
		}
	}

	public void runProcesses() {
		final __ProcessRunnerStartable s   = new __ProcessRunnerStartable(_pq, this);
		final Startable         task  = this._c.con().askConcurrent(s);
		task.start();

		try {
			await()
					.atMost(2, TimeUnit.SECONDS)
					.until(() -> task.isSignalled()|| _c.get_pw().isSignalled());

			for (final CB_Process process : _pq) {
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
		if (_alreadyP.contains(aProcess)) throw new Error();
		_alreadyP.add(aProcess);
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

	private class __ProcessRunnerStartable implements CompFactory.StartableI {
		private final Queue<CB_Process>     procs;
		private final DefaultCompilationBus xxx;

		public __ProcessRunnerStartable(final Queue<CB_Process> aProcs, final DefaultCompilationBus aXxx) {
			procs = aProcs;
			xxx   = aXxx;
		}

		@Override
		public void run() {
			// FIXME passing sh*t between threads (P.O.!)
			_defaultProgressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, INTEGER_MARKER_CODES.DEFAULT_COMPILATION_BUS__RUN_PROCESS__THREAD_ENTER, new Object[]{});
			final CB_Process[] poll = new CB_Process[1];


			//given().ignoreException(IllegalStateException.class).
			await("named await statement") // chasing concurrentcy out of the park

					// eugenp:
					// The status is obtained by a Callable that polls our service at defined intervals
					// (100ms default) after a specified initial delay (default 100ms).
					// Here we are using the default settings for the timeout, interval, and delay:

					//.atMost(1, TimeUnit.SECONDS) // don't overheat fishes
					.atMost(5, TimeUnit.SECONDS) // heat death of universe
					//.catchUncaughtExceptions()
					.conditionEvaluationListener(new ConditionEvaluationListener() {
						@Override
						public void conditionEvaluated(final EvaluatedCondition condition) {
							int y = 2;
						}
					})

					//.ignoreExceptionsInstanceOf()
					.until(() -> {
						poll[0] = procs.poll();
						return poll[0] != null;
					});


			if (poll != null) {
				_defaultProgressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, INTEGER_MARKER_CODES.DEFAULT_COMPILATION_BUS__RUN_PROCESS__EXECUTE_LOG, new Object[]{poll[0].name()});
				poll[0].execute(xxx);
				//recur //also djv
			} else {
				_defaultProgressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, INTEGER_MARKER_CODES.DEFAULT_COMPILATION_BUS__RUN_PROCESS__MAKE_POLLING_EXPENSIVE_AGAIN, new Object[]{poll[0]});
				//try {
				//	Thread.sleep(500);
				//} catch (InterruptedException aE) {
				//}
			}
			_defaultProgressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, INTEGER_MARKER_CODES.DEFAULT_COMPILATION_BUS__RUN_PROCESS__THREAD_EXIT, new Object[]{});
		}

		@Override
		public boolean isSignalled() {
			return false;
		}

		@Override
		public String getThreadName() {
			return "[DefaultCompilationBus]";
		}
	}
}
