package tripleo.elijah.comp.internal;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.i.CB_Action;
import tripleo.elijah.comp.i.CB_Process;
import tripleo.elijah.comp.i.CompilationChange;
import tripleo.elijah.comp.i.CompilerDriver;
import tripleo.elijah.comp.i.ICompilationBus;
import tripleo.elijah.comp.i.ILazyCompilerInstructions;
import tripleo.elijah.comp.i.IProgressSink;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;

import tripleo.elijah.comp.i.CB_Monitor;
import tripleo.elijah.comp.i.ProgressSinkComponent;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import tripleo.elijah_elevated.comp.compilation_bus.DCB_Startable;
import tripleo.elijah_elevated.comp.compilation_bus.SingleActionProcess;

import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.List;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.awaitility.core.ConditionTimeoutException;
import java.util.concurrent.TimeUnit;
import static org.awaitility.Awaitility.await;

public class DefaultCompilationBus implements ICompilationBus {
	private final          CB_Monitor        _monitor;
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
		final DCB_Startable s   = new DCB_Startable(this, pq, _defaultProgressSink);

		final Startable task = this.c.con().askConcurrent(s);
		task.start();

		//final Eventual<Ok> abusingIt = c.get_pw().abusingIt;
		//return abusingIt.isResolved();

		try {
			await()
				.atMost(5, TimeUnit.SECONDS)
				.until(task::isSignalled);
		} catch (ConditionTimeoutException cte) {
			//throw new RuntimeException(cte);
			System.err.println("9997-109 Awaitility timeout in DCB");
		}

		for (final CB_Process process : pq) {
			logProgess(INTEGER_MARKER_CODES.DEFAULT_COMPILATION_BUS__RUN_PROCESS__EXECUTE_LOG, process.name());
			execute_process(this, process);
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
}
