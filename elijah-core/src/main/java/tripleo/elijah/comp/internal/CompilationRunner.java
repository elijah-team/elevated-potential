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
import tripleo.elijah.stateful.*;

import java.util.function.*;

public class CompilationRunner extends _RegistrationTarget implements ICompilationRunner {
	public final @NotNull  EzCache         ezCache = new DefaultEzCache();
	private final @NotNull Compilation     _compilation;
	private final @NotNull ICompilationBus cb;
	@Getter
	private final @NotNull CR_State        crState;
	@Getter
	private final @NotNull IProgressSink   progressSink;
	private final @NotNull CCI             cci;
	@Getter
	private final @NotNull CIS             cis;
	private /*@NotNull*/       CB_StartCompilationRunnerAction startAction;
	private /*@NotNull*/       CR_FindCIs                      cr_find_cis;
	private /*@NotNull*/       CR_AlmostComplete               _CR_AlmostComplete;

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

	public CR_AlmostComplete cr_AlmostComplete() {
		if (this._CR_AlmostComplete == null) {
			this._CR_AlmostComplete = new CR_AlmostComplete();
		}
		return _CR_AlmostComplete;
	}

	public CR_FindCIs cr_find_cis() {
		if (this.cr_find_cis == null) {
			var beginning = _accessCompilation().con().createBeginning(this);
			this.cr_find_cis = new CR_FindCIs(beginning);
		}
		return this.cr_find_cis;
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
			startAction = new CB_StartCompilationRunnerAction(this, (IPipelineAccess) pa, aRootCI);
			// FIXME CompilerDriven vs Process ('steps' matches "CK", so...)
			cb.add(startAction.cb_Process());

			startAction.execute(cb.getMonitor()); // FIXME calling automatically for some reason?
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
