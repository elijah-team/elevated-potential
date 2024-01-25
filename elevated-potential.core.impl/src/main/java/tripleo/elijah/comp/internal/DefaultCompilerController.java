package tripleo.elijah.comp.internal;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.caches.DefaultEzCache;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.ICompilationRunner;
import tripleo.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah.comp.impl.DefaultCompilationEnclosure;
import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;
import tripleo.elijah.comp.specs.EzCache;
import tripleo.elijah.g.GCompilationEnclosure;
import tripleo.elijah.g.GPipelineAccess;
import tripleo.elijah.stateful._RegistrationTarget;
import tripleo.elijah.util.*;
import tripleo.elijah_elevated.lcm.LCM_Event_StartCompilationRunnerAction;

import java.util.List;
import java.util.function.Supplier;

public class DefaultCompilerController implements CompilerController {
	private ICompilationBus     cb;
	private List<CompilerInput> inputs;
	private Compilation         c;
	//private final ICompilationAccess3 ca3;

	public DefaultCompilerController(final ICompilationAccess3 aCa3) {
		//ca3 = aCa3;
		c = aCa3.getComp();
	}

	@Override
	public void setEnclosure(final GCompilationEnclosure aCompilationEnclosure) {
		final CompilationEnclosure ce = (CompilationEnclosure) aCompilationEnclosure;
		_setInputs(ce.getCompilation(), ce.getCompilerInput());
	}

	public void _setInputs(final Compilation0 aCompilation, final List<CompilerInput> aInputs) {
		assert c == aCompilation;
		c      = (Compilation) aCompilation;
		inputs = aInputs;
	}

	@Override
	public void printUsage() {
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_2("Usage: eljc [--showtree] [-sE|O] <directory or .ez file names>");
	}

	@Override
	public Operation<Ok> processOptions() {
		final OptionsProcessor             op                   = new ApacheOptionsProcessor();
		final CompilerInstructionsObserver cio                  = new CompilerInstructionsObserver(c);
		final CompilationEnclosure         compilationEnclosure = (CompilationEnclosure) c.getCompilationEnclosure();

		compilationEnclosure.setCompilationAccess(c.con().createCompilationAccess());
		compilationEnclosure.setCompilationBus(cb = c.con().createCompilationBus());

		c._cis().set_cio(cio);

		return op.process(c, inputs, cb); // TODO 09/08 Make this more complicated
	}

	@Override
	public void runner() {
		runner(new _DefaultCon());
	}

	public void hook(final ICompilationRunner aCr) {

	}

	@Override
	public void runner(final @NotNull Con con) {
		if (false) c.____m();

		c._cis().subscribeTo(c);

		final CompilationEnclosure ce = (CompilationEnclosure) c.getCompilationEnclosure();

		final ICompilationAccess compilationAccess = ce.getCompilationAccess();
		assert compilationAccess != null;

		final CR_State           crState = new CR_State(compilationAccess);
		final ICompilationRunner cr000   = new CompilationRunner(compilationAccess, crState);

		crState.setRunner(cr000);

		ce.setCompilationRunner(cr000);

		hook(cr000);

		cb.add(new CB_FindCIs(cr000, inputs));
		cb.add(new CB_FindStdLibProcess(ce, cr000));


		((DefaultCompilationBus) cb).runProcesses();

		c.getFluffy().checkFinishEventuals();
	}

	@Override
	public CB_Monitor defaultMonitor() {
		return new DefaultCompRunner_Monitor();
	}

	public static class _DefaultCon implements Con {
		@Override
		public ICompilationRunner newCompilationRunner(final ICompilationAccess compilationAccess) {
			final CR_State           crState = new CR_State(compilationAccess);
			final ICompilationRunner cr      = new CompilationRunner(compilationAccess, crState);

			crState.setRunner(cr);

			return cr;
		}
	}

	public static class CompilationRunner extends _RegistrationTarget implements ICompilationRunner {
		public final @NotNull  EzCache         ezCache;
		private final @NotNull Compilation     _compilation;
		private final @NotNull ICompilationBus cb;
		private final @NotNull CR_State        crState;
		@Getter
		private final @NotNull IProgressSink   progressSink;

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

			final CompilationEnclosure compilationEnclosure = (CompilationEnclosure) _compilation.getCompilationEnclosure();

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
			crState      = aCrState;
			ezCache      = new DefaultEzCache((Compilation) aca.getCompilation());
		}

		public void logProgress(final int number, final String text) {
			if (number == 130)
				return;

			SimplePrintLoggerToRemoveSoon.println_err_3("%d %s".formatted(number, text));
		}

		@Override
		public void start(final CompilerInstructions aRootCI, @NotNull final GPipelineAccess pa) {
			((IPipelineAccess) pa).getCompilation().lcm().asv(this, LCM_Event_StartCompilationRunnerAction.instance());
		}

		public CompilationEnclosure getCompilationEnclosure() {
			return (CompilationEnclosure) _accessCompilation().getCompilationEnclosure();
		}

		public Compilation _accessCompilation() {
			return _compilation;
		}

		public EzCache ezCache() {
			return ezCache;
		}

		public Compilation c() {
			return _compilation;
		}

		@Override
		public void nextCi(final CompilerInstructions aCi) {
			_compilation._cis().onNext(aCi);
		}

		public CR_State getCrState() {
			// 24/01/04 back and forth
			return this.crState;
		}
	}

	public static class DefaultCompRunner_Monitor implements CB_Monitor {
		@Override
		public void reportFailure(final CB_Action action, final CB_Output output) {
			SimplePrintLoggerToRemoveSoon.println_err_4("" + output.get());
		}

		@Override
		public void reportSuccess(final CB_Action action, final CB_Output output) {
			int y = 2;
			for (final CB_OutputString outputString : output.get()) {
				SimplePrintLoggerToRemoveSoon.println_out_3("** CompRunnerMonitor ::  " + action.name() + " :: outputString :: " + outputString.getText());
			}
		}
	}

}
