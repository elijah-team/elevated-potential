package tripleo.elijah.comp.process;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.generated.CompilationAlways;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah.comp.internal.CD_CompilationRunnerStart;
import tripleo.elijah.comp.internal.CR_State;
import tripleo.elijah.comp.internal.CompilationRunner;
import tripleo.elijah.util.Operation;

import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

// FIXME 24/01/14 are we checking twice???
public class CB_StartCompilationRunnerAction implements CB_Action, CB_Process {
	@Getter
	private final  CB_Output o;
	private final          CompilationRunner    compilationRunner;
	private final          CompilerInstructions rootCI;
	private final @NotNull IPipelineAccess pa;
	private static         _StartManager   _startManager;

	@Contract(pure = true)
	public CB_StartCompilationRunnerAction(final CompilationRunner aCompilationRunner,
	                                       final @NotNull IPipelineAccess aPipelineAccess,
	                                       final CompilerInstructions aRootCI) {
		compilationRunner = aCompilationRunner;
		pa                = aPipelineAccess;
		rootCI            = aRootCI;

		o = pa.getCompilationEnclosure().getCB_Output(); // new CB_Output();
	}

	public static void setStarted() {
		_startManager.setStarted();
	}

	public static void enjoin(final CD_CompilationRunnerStart aCompilationRunnerStart,
	                          final CompilerInstructions aRootCI,
	                          final CR_State aCRState,
	                          final CB_Output aCbOutput) {
		startManager().enjoin(aCompilationRunnerStart, aRootCI, aCRState, aCbOutput);
	}

	@Contract(value = " -> new", pure = true)
	@NotNull
	public CB_Process cb_Process() {
		return this;
	}

	@Override
	public void execute(CB_Monitor monitor) {
		final CompilerDriver            compilationDriver = pa.getCompilationEnclosure().getCompilationDriver();
		final Operation<CompilerDriven> ocrsd             = compilationDriver.get(CompilationAlways.Tokens.COMPILATION_RUNNER_START);

		switch (ocrsd.mode()) {
			case SUCCESS -> {
				final CD_CompilationRunnerStart compilationRunnerStart = (CD_CompilationRunnerStart) ocrsd.success();
				final CR_State                  crState                = compilationRunner.getCrState();

				assert !isStarted();
				if (isStarted()) {
					//throw new AssertionError();
					tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("twice for " + this);
				} else {
					startManager().enjoin(compilationRunnerStart, rootCI, crState, o);
				}

				monitor.reportSuccess(this, o);
			}
			case FAILURE, NOTHING -> {
				monitor.reportFailure(this, o);
				throw new IllegalStateException("Error");
			}
		}
	}

	public boolean isStarted() {
		return startManager().started();
	}

	private static _StartManager startManager() {
		if (_startManager == null) {
			_startManager = new _StartManager();
		}
		return  _startManager;
	}

	static class _StartManager {
		@Getter
		private static boolean   started;

		public void enjoin(final CD_CompilationRunnerStart aCompilationRunnerStart, final CompilerInstructions aRootCI, final CR_State aCrState, final CB_Output aCBOutput) {
			aCompilationRunnerStart.start(aRootCI, aCrState, aCBOutput);
			setStarted();
		}

		public void setStarted() {
			started = true;
		}

		public boolean started() {
			return started;
		}
	}

	@Contract(pure = true)
	@Override
	public @NotNull String name() {
		return "StartCompilationRunnerAction";
	}

	@Override
	@NotNull
	public List<CB_Action> steps() {
		return List_of(CB_StartCompilationRunnerAction.this);
	}

	public @NotNull CB_Output getO() {
		// back and forth
		return this.o;
	}
}
