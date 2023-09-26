package tripleo.elijah.comp.internal;

import lombok.*;
import org.apache.commons.lang3.tuple.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.caches.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.nextgen.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.stateful.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.util.*;
import java.util.function.*;

import static tripleo.elijah.nextgen.query.Mode.*;

public class CompilationRunner extends _RegistrationTarget {
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
	private /*@NotNull*/       CB_StartCompilationRunnerAction startAction;
	private /*@NotNull*/       CR_FindCIs                      cr_find_cis;
	private /*@NotNull*/       CR_AlmostComplete               _CR_AlmostComplete;

	public CompilationRunner(final @NotNull ICompilationAccess aca, final CR_State aCrState) {
		this(
				aca,
				aCrState,
				() -> aca.getCompilation().getCompilationEnclosure().getCompilationBus()
		);
	}

	public CompilationRunner(final @NotNull ICompilationAccess aca,
	                         final @NotNull CR_State aCrState,
	                         final Supplier<ICompilationBus> scb) {
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

/*
	public @NotNull Operation<CompilerInstructions> parseEzFile(final @NotNull SourceFileParserParams p) throws FileNotFoundException {
		@NotNull Operation<CompilerInstructions> oci;
		final File                               f      = p.f();
		final EzSpec                             ezSpec = p.getEzSpec();
		//logProgress(IProgressSink.Codes.EzM__parseEzFile1, file_name.getAbsolutePath());
		if (!f.exists()) {
			_compilation.getErrSink().reportError("File doesn't exist " + f.getAbsolutePath());

			oci = Operation.failure(new FileNotFoundException());
		} else {
			final Operation<CompilerInstructions> oci1 = CX_realParseEzFile2.realParseEzFile(_compilation, ezSpec, ezCache);

			if (*/
/* false || *//*
 oci1.mode() == SUCCESS) {

			}
			oci = oci1;
		}

		_compilation.getInputTree().setNodeOperation(p.input(), oci);

		return oci;
	}
*/

	public void start(final CompilerInstructions aRootCI, final @NotNull IPipelineAccess pa) {
		// FIXME only run once 06/16
		if (startAction == null) {
			startAction = new CB_StartCompilationRunnerAction(this, pa, aRootCI);
			// FIXME CompilerDriven vs Process ('steps' matches "CK", so...)
			cb.add(startAction.cb_Process());

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
}
