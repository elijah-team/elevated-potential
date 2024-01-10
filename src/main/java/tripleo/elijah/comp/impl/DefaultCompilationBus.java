package tripleo.elijah.comp.impl;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.subscription.BackPressureStrategy;
import io.smallrye.mutiny.subscription.MultiEmitter;
import lombok.Getter;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal.CompilationRunner;
import tripleo.elijah.comp.nextgen.pw.PW_Controller;
import tripleo.elijah.comp.nextgen.pw.PW_PushWork;
import tripleo.elijah.comp.process.DefaultCompilerDriver;
import tripleo.elijah.comp.internal_move_soon.*;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static tripleo.elijah.util.Helpers.*;

public class DefaultCompilationBus implements ICompilationBus {
	public static final int  DEFAULT_COMPILATION_BUS__RUN_PROCESS__EXECUTE_LOG = 5757;

	private final CB_Monitor _monitor;
	@Getter
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
		var m = Multi.createFrom().emitter((MultiEmitter<? super PW_PushWork> emitter) -> {
			final List<CB_Process> pql = pq.stream().collect(Collectors.toList());
			for (CB_Process process : pql) {

				emitter.emit(new PW_PushWork() {
					@Override
					public void handle(final PW_Controller pwc, final PW_PushWork otherInstance) {
						System.err.println("777111 "+process.name()); // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
						logProgess(DEFAULT_COMPILATION_BUS__RUN_PROCESS__EXECUTE_LOG, process.name());
						execute_process(DefaultCompilationBus.this, process);
					}

					@Override
					public void execute(final PW_Controller aController) {
						handle(aController, null);
					}
				});
			}

			// Once all items are emitted, complete the emitter
			emitter.complete();
		}, BackPressureStrategy.BUFFER);

		m.subscribe().with((PW_PushWork item) -> {

			item.handle(c.__pw_controller(), null);

			//System.out.println("Received item: " + item);
		});
	}

	private void logProgess(final int code, final String message) {
		SimplePrintLoggerToRemoveSoon.println_out_4(""+code+" "+message);
	}

	private void execute_process(final DefaultCompilationBus aDefaultCompilationBus,
	                             final CB_Process aProcess) {
		assert aDefaultCompilationBus == this;

		//CompilationUnitTree
		//Compilation.Cheat.executeCB_Action(aProcess);

		aProcess.execute(this);
	}

	public static class SingleActionProcess implements CB_Process {
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

	@Override
	public CompilerDriver getCompilerDriver() {
		// 24/01/04 back and forth
		return this.compilerDriver;
	}
}
