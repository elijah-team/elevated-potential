package tripleo.elijah.comp.internal;

import lombok.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.caches.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.stateful.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.util.*;
import java.util.function.*;

public class CompilationRunner extends _RegistrationTarget {
	public enum ST {
		;

		static class ExitConvertUserTypes implements State {
			private StateRegistrationToken identity;

			@Override
			public void apply(final DefaultStateful element) {
				// final VariableTableEntry vte = ((DeduceElement3_VariableTableEntry)
				// element).principal;

				// final DeduceTypes2 dt2 = ((DeduceElement3_VariableTableEntry)
				// element).deduceTypes2();
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
				// final VariableTableEntry vte = ((DeduceElement3_VariableTableEntry)
				// element).principal;
			}

			@Override
			public boolean checkState(final DefaultStateful aElement3) {
				// return ((DeduceElement3_VariableTableEntry) aElement3).st ==
				// DeduceElement3_VariableTableEntry.ST.INITIAL;
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

		public static State EXIT_CONVERT_USER_TYPES;

		public static State EXIT_RESOLVE;

		public static State INITIAL;

		public static void register(final @NotNull _RegistrationTarget art) {
			// EXIT_RESOLVE = registerState(new ST.ExitResolveState());
			INITIAL = art.registerState(new ST.InitialState());
			// EXIT_CONVERT_USER_TYPES = registerState(new ST.ExitConvertUserTypes());
		}
	}

	private final EzCache ezCache = new DefaultEzCache();
	private final Compilation _compilation;
	private final ICompilationBus cb;
	@Getter
	private final CR_State crState;
	@Getter
	private final @NotNull IProgressSink progressSink;
	private final @NotNull CCI cci;
	private final EzM ezm;
	@Getter
	private final CIS cis;
	private CB_StartCompilationRunnerAction startAction;
	private CR_FindCIs cr_find_cis;

	private CR_AlmostComplete _CR_AlmostComplete;

	public CompilationRunner(final @NotNull ICompilationAccess aca, final CR_State aCrState) {
		_compilation = aca.getCompilation();

		_compilation.getCompilationEnclosure().setCompilationAccess(aca);

		cis = _compilation._cis();
		cb = _compilation.getCompilationEnclosure().getCompilationBus();

		assert cb != null;

		progressSink = cb.defaultProgressSink();

		cci = new DefaultCCI(_compilation, cis, progressSink);
		crState = aCrState;

		CompilationRunner.ST.register(this);
	}

	public CompilationRunner(final @NotNull ICompilationAccess aca, final CR_State aCrState,
			final Supplier<DefaultCompilationBus> scb) {
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

		cci = new DefaultCCI(_compilation, cis, progressSink);
		crState = aCrState;

		CompilationRunner.ST.register(this);
		ezm = new EzM(_compilation.getCompilationEnclosure());
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

	public @NotNull Operation<CompilerInstructions> parseEzFile(final @NotNull SourceFileParserParams p) {
		final Operation<CompilerInstructions> oci = ezm.parseEzFile1(p);
		assert oci != null;

		_compilation.getInputTree().setNodeOperation(p.input(), oci);

		return oci;
	}

	/**
	 * - I don't remember what absolutePath is for - Cache doesn't add to QueryDB
	 * <p>
	 * STEPS ------
	 * <p>
	 * 1. Get absolutePath 2. Check cache, return early 3. Parse (Query is incorrect
	 * I think) 4. Cache new result
	 *
	 * @param spec
	 * @param cache
	 * @return
	 */
	public Operation<CompilerInstructions> realParseEzFile(final EzSpec spec, final EzCache cache) {
		final @NotNull File file = spec.file();

		final String absolutePath;
		try {
			absolutePath = file.getCanonicalFile().toString();
		} catch (final IOException aE) {
			return Operation.failure(aE);
		}

		final Optional<CompilerInstructions> early = cache.get(absolutePath);

		if (early.isPresent()) {
			return Operation.success(early.get());
		}

		final Operation<CompilerInstructions> cio = CX_ParseEzFile.parseAndCache(spec, ezCache(), absolutePath);
		return cio;
	}

	public @NotNull Operation<CompilerInstructions> realParseEzFile(final @NotNull SourceFileParserParams p) {
		final Operation<CompilerInstructions> oci = ezm.realParseEzFile(p);

		CompilerInput input = p.input();
		if (input != null) {
			_compilation.getInputTree().setNodeOperation(input, oci);
		}

//		return oci;

		File f = p.f();
		try {
			InputStream s = p.cc().getCompilation().getIO().readFile(f);
			var oci2 = realParseEzFile(new EzSpec(p.file_name(), s, f), ezCache);
			return oci2;
		} catch (FileNotFoundException aE) {
			throw new RuntimeException(aE);
		}
	}

	public void start(final CompilerInstructions aRootCI, final @NotNull IPipelineAccess pa) {
		// FIXME only run once 06/16
		if (startAction == null) {
			startAction = new CB_StartCompilationRunnerAction(this, pa, aRootCI);
			// FIXME CompilerDriven vs Process ('steps' matches "CK", so...)
			cb.add(startAction.cb_Process());
		}

		startAction.execute(new CB_Monitor() {
			@Override
			public void reportFailure(final CB_Action aCBAction, final CB_Output aCB_output) {
				System.err.println(aCB_output.get());
			}

			@Override
			public void reportSuccess(final CB_Action aCBAction, final CB_Output aCB_output) {
				final List<CB_OutputString> x = aCB_output.get();
				for (CB_OutputString xx : x) {
					System.err.println("127 " + xx.getText());
				}
			}
		});
	}

}
