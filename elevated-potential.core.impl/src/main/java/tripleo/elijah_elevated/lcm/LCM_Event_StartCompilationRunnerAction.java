package tripleo.elijah_elevated.lcm;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.ICompilationRunner;
import tripleo.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

public class LCM_Event_StartCompilationRunnerAction implements LCM_Event {
	private static LCM_Event_StartCompilationRunnerAction INSTANCE = new LCM_Event_StartCompilationRunnerAction();
	private CB_StartCompilationRunnerAction startAction;

	public static LCM_Event instance() {
		return INSTANCE;
	}

	@Override
	public void handle(final LCM_HandleEvent aHandleEvent) {
		final CompilationEnclosure compilationEnclosure = aHandleEvent.compilation().c().getCompilationEnclosure();
		final CompilerInstructions aRootCI              = compilationEnclosure.getCompilation().getRootCI();
		final IPipelineAccess      pa                   = compilationEnclosure.getPipelineAccess();
		final ICompilationBus      cb                   = compilationEnclosure.getCompilationBus();
		final CompilationRunner    runner               = compilationEnclosure.getCompilationRunner();
		final CR_State             crState              = runner.getCrState(); // effectively a global, so what's the point? to localize the data calls.
		final ICompilationRunner    runner               = compilationEnclosure.getCompilationRunner();

		if (startAction == null) {
			startAction = new CB_StartCompilationRunnerAction(runner, pa, aRootCI);
			// FIXME CompilerDriven vs Process ('steps' matches "CK", so...)
			cb.add(startAction.cb_Process());
		}

		if (false) {
			// FIXME calling automatically for some reason?
			final CB_Monitor                monitor           = cb.getMonitor();
			final CompilerDriver            compilationDriver = pa.getCompilationEnclosure().getCompilationDriver();
			final Operation<CompilerDriven> ocrsd             = compilationDriver.get(CompilationImpl.CompilationAlways.Tokens.COMPILATION_RUNNER_START);

			final @NotNull CB_Output cbOutput = startAction.getO();

			switch (ocrsd.mode()) {
			case SUCCESS -> {
				final CD_CompilationRunnerStart compilationRunnerStart = (CD_CompilationRunnerStart) ocrsd.success();

				// assert !(started);
				if (CB_StartCompilationRunnerAction.started) {
					//throw new AssertionError();
					SimplePrintLoggerToRemoveSoon.println_err_4("twice for " + startAction);
				} else {
					compilationRunnerStart.start(aRootCI, crState, cbOutput);
					CB_StartCompilationRunnerAction.started = true;
				}

				monitor.reportSuccess(startAction, cbOutput);
			}
			case FAILURE, NOTHING -> {
				monitor.reportFailure(startAction, cbOutput);
				throw new IllegalStateException("Error");
			}
			}
		}

		throw new UnintendedUseException("impl me");
	}

	static class CB_StartCompilationRunnerAction implements CB_Action, CB_Process {
		static        boolean              started;
		private final ICompilationRunner   compilationRunner;
		private final CompilerInstructions rootCI;
		private final @NotNull IPipelineAccess pa;
		@Getter
		final CB_Output o;

		@Contract(pure = true)
		public CB_StartCompilationRunnerAction(final ICompilationRunner aCompilationRunner,
											   final @NotNull IPipelineAccess aPa,
											   final CompilerInstructions aRootCI) {
			compilationRunner = aCompilationRunner;
			pa                = aPa;
			rootCI            = aRootCI;

			o = pa.getCompilationEnclosure().getCB_Output(); // new CB_Output();
		}

		@Contract(value = " -> new", pure = true)
		@NotNull
		public CB_Process cb_Process() {
			return this;
		}

		@Override
		public void execute(CB_Monitor monitor) {
			final CompilerDriver            compilationDriver = pa.getCompilationEnclosure().getCompilationDriver();
			final Operation<CompilerDriven> ocrsd             = compilationDriver.get(CompilationImpl.CompilationAlways.Tokens.COMPILATION_RUNNER_START);

			switch (ocrsd.mode()) {
			case SUCCESS -> {
				final CD_CompilationRunnerStart compilationRunnerStart = (CD_CompilationRunnerStart) ocrsd.success();
				final CR_State                  crState                = compilationRunner.getCrState();

	//			assert !(started);
				if (started) {
					//throw new AssertionError();
					SimplePrintLoggerToRemoveSoon.println_err_4("twice for "+this);
				} else {
					compilationRunnerStart.start(rootCI, crState, o);
					started = true;
				}

				monitor.reportSuccess(this, o);
			}
			case FAILURE, NOTHING -> {
				monitor.reportFailure(this, o);
				throw new IllegalStateException("Error");
			}
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
			// 24/01/04 back and forth
			return this.o;
		}
	}
}
