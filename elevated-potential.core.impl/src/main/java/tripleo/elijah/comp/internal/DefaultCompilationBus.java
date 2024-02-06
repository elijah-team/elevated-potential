package tripleo.elijah.comp.internal;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import static tripleo.elijah.util.Helpers.List_of;

import static org.awaitility.Awaitility.await;
import org.awaitility.core.ConditionTimeoutException;

import tripleo.elijah.util.Eventual;

import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.i.*;

import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

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
				final CompilationChange compilationChange = (CompilationChange) class1.getDeclaredConstructor(new Class[]{}).newInstance(); // ??
				//c.getCompilationEnclosure().getCompilationBus()
				this
						.option(compilationChange);
				assert false;
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException |
					 NoSuchMethodException e) {
				throw new Error();
			}
		}
	}

	public void runProcesses() {
		final var xxx = this;
		var s = new CompFactory.StartableI() {
			@Override
			public void run() {
				if (true) {
					// FIXME passing sh*t between threads (P.O.!)
					_defaultProgressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, 5784, new Object[]{});

					final Eventual<CB_Process>[] ecp = new Eventual[]{new Eventual<>()};
					ecp[0].then(cbp-> {
						cbp.execute(xxx);
						ecp[0] = new Eventual<>(); // this is the ComodificationError pattern
					});

					await()
							.atMost(3, TimeUnit.SECONDS)
							.until(()->ecp[0].isResolved());

					_defaultProgressSink.note(IProgressSink.Codes.DefaultCompilationBus__pollProcess, ProgressSinkComponent.DefaultCompilationBus, 5789, new Object[]{});
				} else {
					System.err.println("9998-124 skip runProcesses");
				}
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
			// TODO 10/20 Remove this soon
			final Thread thread = task.stealThread();

			thread.join();//TimeUnit.MINUTES.toMillis(1));

			try {
				await()
						.atMost(5, TimeUnit.SECONDS)
						.until(() -> {
							return task.isSignalled();
							//final Eventual<Ok> abusingIt = c.get_pw().abusingIt;
							//return abusingIt.isResolved();
						});

				System.err.println("174 ");

				for (final CB_Process process : pq) {
					logProgess(INTEGER_MARKER_CODES.DEFAULT_COMPILATION_BUS__RUN_PROCESS__EXECUTE_LOG, process.name());
					execute_process(this, process);
				}
			} catch (ConditionTimeoutException cte) {
				System.err.println("9998-158 cte timeout in DefaultCompilationBus");
			}
			thread.stop();
		} catch (InterruptedException aE) {
			throw new RuntimeException(aE);
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
