package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.stateful.DefaultStateful;
import tripleo.elijah.stateful.State;
import tripleo.elijah.stateful.StateRegistrationToken;
import tripleo.elijah.stateful._RegistrationTarget;
import tripleo.elijah.util.Operation;

import java.util.function.Supplier;

public class CompilationRunner extends _RegistrationTarget {
	private final          Compilation     _compilation;
	private final          ICompilationBus cb;
	private final          CR_State        crState;
	private final @NotNull IProgressSink   progressSink;
	private final @NotNull CCI             cci;
	private final          EzM             ezm = new EzM();
	private final          CIS             cis;
	private CB_StartCompilationRunnerAction startAction;
	private                CR_FindCIs cr_find_cis;

	public CompilationRunner(final @NotNull ICompilationAccess aca, final CR_State aCrState) {
		_compilation = aca.getCompilation();

		_compilation.getCompilationEnclosure().setCompilationAccess(aca);

		cis = _compilation._cis();
		cb  = _compilation.getCompilationEnclosure().getCompilationBus();

		assert cb != null;

		progressSink = cb.defaultProgressSink();

		cci     = new DefaultCCI(_compilation, cis, progressSink);
		crState = aCrState;

		CompilationRunner.ST.register(this);
	}

	public CompilationRunner(final @NotNull ICompilationAccess aca, final CR_State aCrState, final Supplier<CompilationBus> scb) {
		_compilation = aca.getCompilation();

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

		CompilationRunner.ST.register(this);
	}

	public void logProgress(final int number, final String text) {
		if (number == 130) return;

		tripleo.elijah.util.Stupidity.println_err_3("%d %s".formatted(number, text));
	}

	public @NotNull Operation<CompilerInstructions> parseEzFile(final @NotNull SourceFileParserParams p) {
		final Operation<CompilerInstructions> oci = ezm.parseEzFile1(p);
		assert oci != null;

		_compilation.getInputTree().setNodeOperation(p.input(), oci);

		return oci;
	}

	public @NotNull Operation<CompilerInstructions> realParseEzFile(final @NotNull SourceFileParserParams p) {
		final Operation<CompilerInstructions> oci = ezm.realParseEzFile(p);

		_compilation.getInputTree().setNodeOperation(p.input(), oci);

		return oci;
	}

	public CR_FindCIs cr_find_cis() {
		if (this.cr_find_cis == null) {
			var beginning = _accessCompilation().beginning(this);
			this.cr_find_cis = new CR_FindCIs(beginning);
		}
		return this.cr_find_cis;
	}

	boolean started;

	public Compilation _accessCompilation() {
		return _compilation;
	}

	public CR_State getCrState() {
		return crState;
	}

	public IProgressSink getProgressSink() {
		return progressSink;
	}

	public CIS getCis() {
		return cis;
	}

	public void start(final CompilerInstructions ci, final @NotNull IPipelineAccess pa) {
		// FIXME only run once 06/16
		if (startAction == null) {
			startAction = new CB_StartCompilationRunnerAction(this, pa, ci);
			// FIXME CompilerDriven vs Process ('steps' matches "CK", so...)
			cb.add(startAction.cb_Process());
		}

		if (!started) {
			started = true;
			CB_Monitor monitor = new CB_Monitor() {
				@Override
				public void reportFailure(final CB_Action aCBAction, final CB_Output aCB_output) {

				}

				@Override
				public void reportSuccess(final CB_Action aCBAction, final CB_Output aCB_output) {

				}
			};

			int bp = 2;

			for (CB_Process process : cb.processes()) {
				process.steps().forEach(s -> s.execute(monitor));
			}
		}
	}

	public enum ST {
		;

		public static State EXIT_CONVERT_USER_TYPES;
		public static State EXIT_RESOLVE;
		public static State INITIAL;

		public static void register(final @NotNull _RegistrationTarget art) {
			//EXIT_RESOLVE            = registerState(new ST.ExitResolveState());
			INITIAL = art.registerState(new ST.InitialState());
			//EXIT_CONVERT_USER_TYPES = registerState(new ST.ExitConvertUserTypes());
		}

		static class ExitConvertUserTypes implements State {
			private StateRegistrationToken identity;

			@Override
			public void apply(final DefaultStateful element) {
				//final VariableTableEntry vte = ((DeduceElement3_VariableTableEntry) element).principal;

				//final DeduceTypes2         dt2     = ((DeduceElement3_VariableTableEntry) element).deduceTypes2();
			}

			@Override
			public boolean checkState(final DefaultStateful aElement3) {
				return true;
			}

			@Override
			public void setIdentity(final StateRegistrationToken aId) {
				identity = aId;
			}
		}

		static class ExitResolveState implements State {

			private StateRegistrationToken identity;

			@Override
			public void apply(final DefaultStateful element) {
				//final VariableTableEntry vte = ((DeduceElement3_VariableTableEntry) element).principal;
			}

			@Override
			public boolean checkState(final DefaultStateful aElement3) {
				//return ((DeduceElement3_VariableTableEntry) aElement3).st == DeduceElement3_VariableTableEntry.ST.INITIAL;
				return false; // FIXME
			}

			@Override
			public void setIdentity(final StateRegistrationToken aId) {
				identity = aId;
			}
		}

		static class InitialState implements State {
			private StateRegistrationToken identity;

			@Override
			public void apply(final DefaultStateful element) {

			}

			@Override
			public boolean checkState(final DefaultStateful aElement3) {
				return true;
			}

			@Override
			public void setIdentity(final StateRegistrationToken aId) {
				identity = aId;
			}
		}
	}
}
