package tripleo.elijah.comp.impl;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.helpers.queues.MpscLinkedQueue;
import io.smallrye.mutiny.subscription.BackPressureStrategy;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.hairball.__MultiEmitterConsumer;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal.CompilationRunner;
import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;
import tripleo.elijah.comp.nextgen.pw.PW_PushWork;
import tripleo.elijah.comp.process.DefaultCompilerDriver;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Queue;

public class DefaultCompilationBus implements ICompilationBus {
	public static final int DEFAULT_COMPILATION_BUS__RUN_PROCESS__EXECUTE_LOG = 5757;

	private final          CB_Monitor        _monitor;
	@Getter
	private final @NotNull CompilerDriver    compilerDriver;
	private final @NotNull Compilation       c;
	private final          Queue<CB_Process> pq = new MpscLinkedQueue<>();
	private final @NotNull IProgressSink     _defaultProgressSink;

	public DefaultCompilationBus(final @NotNull CompilationEnclosure ace) {
		c                    = (@NotNull Compilation) ace.getCompilationAccess().getCompilation();
		_monitor             = new CompilationRunner.__CompRunner_Monitor();
		_defaultProgressSink = new DefaultProgressSink();

		compilerDriver = new DefaultCompilerDriver(this);
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
		final __MultiEmitterConsumer multiEmitterConsumer = new __MultiEmitterConsumer(this, pq, null);
		final Multi<PW_PushWork>     m                    = Multi.createFrom().emitter(multiEmitterConsumer, BackPressureStrategy.BUFFER);

		m.subscribe().with((PW_PushWork item) -> {
			//System.out.println("Received item: " + item);

			item.handle(c.__pw_controller(), null);
		});
	}

	public void logProgress(final int code, final String message) {
		SimplePrintLoggerToRemoveSoon.println_out_4("" + code + " " + message);
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

	@Override
	public CompilerDriver getCompilerDriver() {
		// 24/01/04 back and forth
		return this.compilerDriver;
	}
}
