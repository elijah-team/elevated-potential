package tripleo.elijah.comp.internal;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.caches.DefaultEzCache;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.*;
import tripleo.elijah.comp.impl.DefaultCompilationEnclosure;
import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;
import tripleo.elijah.comp.specs.EzCache;
import tripleo.elijah.stateful._RegistrationTarget;
import tripleo.elijah.util.Operation;

import java.util.function.Supplier;

public class CompilationRunner extends _RegistrationTarget implements ICompilationRunner {
	public final @NotNull  EzCache                         ezCache = new DefaultEzCache();
	private final @NotNull Compilation                     _compilation;
	private final @NotNull ICompilationBus                 cb;
	@Getter
	private final @NotNull CR_State                        crState;
	@Getter
	private final @NotNull IProgressSink                   progressSink;
	private final @NotNull CCI                             cci;
	@Getter
	private final @NotNull CIS                             cis;
	private /*@NotNull*/   CB_StartCompilationRunnerAction startAction;

	public CompilationRunner(final @NotNull ICompilationAccess aca, final CR_State aCrState) {
		this(
				aca,
				aCrState,
				() -> ((DefaultCompilationEnclosure) aca.getCompilation().getCompilationEnclosure()).getCompilationBus()
			);
	}

	public CompilationRunner(final @NotNull ICompilationAccess aca,
							 final @NotNull CR_State aCrState,
							 final Supplier<ICompilationBus> scb) {
		_compilation = (Compilation) aca.getCompilation();

		_compilation.getCompilationEnclosure().setCompilationAccess(aca);

		cis = _compilation._cis();

		var cb1 = _compilation.getCompilationEnclosure().getCompilationBus();

		if (cb1 == null) {
			cb = scb.get();
			_compilation.getCompilationEnclosure().setCompilationBus(cb);
		} else {
			cb = _compilation.getCompilationEnclosure().getCompilationBus();
		}

		progressSink = cb.defaultProgressSink();

		cci     = new DefaultCCI(_compilation, cis, progressSink);
		crState = aCrState;
	}

	public Compilation _accessCompilation() {
		return _compilation;
	}

	public CIS _cis() {
		return cis;
	}

	public Compilation c() {
		return _compilation;
	}

	public EzCache ezCache() {
		return ezCache;
	}

	public CompilationEnclosure getCompilationEnclosure() {
		return _accessCompilation().getCompilationEnclosure();
	}

	public void logProgress(final int number, final String text) {
		if (number == 130)
			return;

		tripleo.elijah.util.Stupidity.println_err_3("%d %s".formatted(number, text));
	}

	@Override
	public void start(final CompilerInstructions aRootCI, @NotNull final GPipelineAccess pa) {
		// FIXME only run once 06/16
		if (startAction == null) {
			CB_StartCompilationRunnerAction startAction = new CB_StartCompilationRunnerAction(this, (IPipelineAccess) pa, aRootCI);
			// FIXME CompilerDriven vs Process ('steps' matches "CK", so...)
			cb.add(startAction.cb_Process());

			// FIXME calling automatically for some reason?
			final CB_Monitor                monitor           = cb.getMonitor();
			final CompilerDriver            compilationDriver = ((IPipelineAccess)pa).getCompilationEnclosure().getCompilationDriver();
			final Operation<CompilerDriven> ocrsd             = compilationDriver.get(CompilationImpl.CompilationAlways.Tokens.COMPILATION_RUNNER_START);

			final @NotNull CB_Output cbOutput = startAction.getO();

			switch (ocrsd.mode()) {
			case SUCCESS -> {
				final CD_CompilationRunnerStart compilationRunnerStart = (CD_CompilationRunnerStart) ocrsd.success();
				final CR_State                  crState1               = this.getCrState();

	//			assert !(started);
				if (CB_StartCompilationRunnerAction.started) {
					//throw new AssertionError();
					System.err.println("twice for "+ startAction);
				} else {
					compilationRunnerStart.start(aRootCI, crState1, cbOutput);
					CB_StartCompilationRunnerAction.started = true;
				}

				monitor.reportSuccess(startAction, cbOutput);
			}
			case FAILURE, NOTHING -> {
				monitor.reportFailure(startAction, cbOutput);
				throw new IllegalStateException("Error");
			}
			}
		} else {
			assert false;
		}
	}

	public static class __CompRunner_Monitor implements CB_Monitor {
		@Override
		public void reportFailure(final CB_Action action, final CB_Output output) {
			System.err.println(output.get());
		}

		@Override
		public void reportSuccess(final CB_Action action, final CB_Output output) {
			for (final CB_OutputString outputString : output.get()) {
//				Stupidity.println_out_3("** CompRunnerMonitor ::  " + action.name() + " :: outputString :: " + outputString.getText());
			}
		}
	}
}
