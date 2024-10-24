package tripleo.elijah_elevated_durable.compilation_bus;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.ICompilationRunner;
import tripleo.elijah.comp.specs.EzCache;
import tripleo.elijah.g.GPipelineAccess;
import tripleo.elijah.stateful._RegistrationTarget;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_elevateder.comp.i.Compilation;
import tripleo.elijah_elevateder.comp.caches.DefaultEzCache;
import tripleo.elijah_elevateder.comp.i.extra.IPipelineAccess;
import tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon;

import java.util.function.Supplier;

public class EDL_CompilationRunner extends _RegistrationTarget implements ICompilationRunner {
	public final @NotNull  EzCache                         ezCache;
	private final @NotNull Compilation                     _compilation;
	private final @NotNull ICompilationBus cb;
	@Getter
	private final @NotNull CR_State        crState;
	@Getter
	private final @NotNull IProgressSink   progressSink;
	private /*@NotNull*/   CB_StartCompilationRunnerAction startAction;

	public EDL_CompilationRunner(final @NotNull ICompilationAccess aca, final CR_State aCrState) {
		this(
				aca,
				aCrState,
				() -> ((EDL_CompilationEnclosure) aca.getCompilation().getCompilationEnclosure()).getCompilationBus()
			);
	}

	public EDL_CompilationRunner(final @NotNull ICompilationAccess aca,
	                             final @NotNull CR_State aCrState,
	                             final Supplier<ICompilationBus> scb) {
		_compilation = (Compilation) aca.getCompilation();

		final CompilationEnclosure compilationEnclosure = _compilation.getCompilationEnclosure();

		compilationEnclosure.setCompilationAccess(aca);

		//final @NotNull CIS    cis = _compilation._cis();
		final ICompilationBus compilationBus = compilationEnclosure.getCompilationBus();

		if (compilationBus == null) {
			cb = scb.get();
			compilationEnclosure.setCompilationBus(cb);
		} else {
			cb = compilationEnclosure.getCompilationBus();
		}

		progressSink = cb.defaultProgressSink();
		crState = aCrState;
		ezCache      = new DefaultEzCache((Compilation) aca.getCompilation());
	}

	public Compilation _accessCompilation() {
		return _compilation;
	}

	public EDL_CIS _cis() {
		return _compilation._cis();
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

		SimplePrintLoggerToRemoveSoon.println_err_3("%d %s".formatted(number, text));
	}

	@Override
	public void start(final CompilerInstructions aRootCI, @NotNull final GPipelineAccess pa) {
		// FIXME only run once 06/16
		if (startAction == null) {
			startAction = new CB_StartCompilationRunnerAction(this, (IPipelineAccess) pa, aRootCI);

			// FIXME CompilerDriven vs Process ('steps' matches "CK", so...)
			final CB_Process process = startAction.cb_Process();
			cb.add(process);
		} else {
			assert false;
		}
	}

	public static class __CompRunner_Monitor implements CB_Monitor {
		@Override
		public void reportFailure(final CB_Action action, final CB_Output output) {
			SimplePrintLoggerToRemoveSoon.println_err_4(""+output.get());
		}

		@Override
		public void reportSuccess(final CB_Action action, final CB_Output output) {
			int y=2;
			for (final CB_OutputString outputString : output.get()) {
				SimplePrintLoggerToRemoveSoon.println_out_3("** CompRunnerMonitor ::  " + action.name() + " :: outputString :: " + outputString.getText());
			}
		}
	}

	public CR_State getCrState() {
		// 24/01/04 back and forth
		return this.crState;
	}
}
