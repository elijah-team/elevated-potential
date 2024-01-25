package tripleo.elijah.comp.internal;

import lombok.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.caches.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.*;
import tripleo.elijah.comp.impl.*;
import tripleo.elijah.comp.internal_move_soon.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.g.*;
import tripleo.elijah.stateful.*;
import tripleo.elijah.util.*;
import tripleo.elijah_elevated.lcm.LCM_Event_StartCompilationRunnerAction;

import java.util.function.*;

public class CompilationRunner extends _RegistrationTarget implements ICompilationRunner {
	public final @NotNull  EzCache                         ezCache;
	private final @NotNull Compilation                     _compilation;
	private final @NotNull ICompilationBus                 cb;
	private final @NotNull CR_State                        crState;
	@Getter
	private final @NotNull IProgressSink                   progressSink;

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

		final CompilationEnclosure compilationEnclosure = _compilation.getCompilationEnclosure();

		compilationEnclosure.setCompilationAccess(aca);

		//final @NotNull CIS    cis = _compilation._cis();
		ICompilationBus compilationBus;

		compilationBus = compilationEnclosure.getCompilationBus();
		if (compilationBus == null) {
			compilationBus = scb.get();
			compilationEnclosure.setCompilationBus(compilationBus);
		}

		compilationBus = compilationEnclosure.getCompilationBus();
		assert compilationBus != null;
		cb = compilationBus;

		progressSink = cb.defaultProgressSink();
		crState = aCrState;
		ezCache      = new DefaultEzCache((Compilation) aca.getCompilation());
	}

	public Compilation _accessCompilation() {
		return _compilation;
	}

	public CIS _cis() {
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

		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_3("%d %s".formatted(number, text));
	}

	@Override
	public void start(final CompilerInstructions aRootCI, @NotNull final GPipelineAccess pa) {
		((IPipelineAccess) pa).getCompilation().lcm().asv(this, LCM_Event_StartCompilationRunnerAction.instance());
	}

	public static class __CompRunner_Monitor implements CB_Monitor {
		@Override
		public void reportFailure(final CB_Action action, final CB_Output output) {
			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4(""+output.get());
		}

		@Override
		public void reportSuccess(final CB_Action action, final CB_Output output) {
			int y=2;
			for (final CB_OutputString outputString : output.get()) {
				tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_3("** CompRunnerMonitor ::  " + action.name() + " :: outputString :: " + outputString.getText());
			}
		}
	}

	public CR_State getCrState() {
		// 24/01/04 back and forth
		return this.crState;
	}
}
