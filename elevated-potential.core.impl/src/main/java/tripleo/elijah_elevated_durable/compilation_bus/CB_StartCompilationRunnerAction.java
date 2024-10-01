package tripleo.elijah_elevated_durable.compilation_bus;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_fluffy.util.Operation;
import tripleo.elijah_elevated_durable.comp.CR_State;
import tripleo.elijah_elevated_durable.comp.EDL_Compilation;
import tripleo.elijah_elevated_durable.comp_driven.CD_CompilationRunnerStart;
import tripleo.elijah_elevateder.comp.i.extra.IPipelineAccess;
import tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon;

import java.util.List;

import static tripleo.elijah_fluffy.util.Helpers.List_of;

class CB_StartCompilationRunnerAction implements CB_Action, CB_Process {
	static        boolean               started;
	private final EDL_CompilationRunner compilationRunner;
	private final CompilerInstructions  rootCI;
	private final @NotNull IPipelineAccess pa;
	@Getter
	final CB_Output o;

	@Contract(pure = true)
	public CB_StartCompilationRunnerAction(final EDL_CompilationRunner aCompilationRunner,
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
		final Operation<CompilerDriven> ocrsd             = compilationDriver.get(EDL_Compilation.CompilationAlways.Tokens.COMPILATION_RUNNER_START);

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
