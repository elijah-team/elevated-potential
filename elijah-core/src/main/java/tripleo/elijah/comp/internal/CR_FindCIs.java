package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.nextgen.query.Mode;
import tripleo.elijah.stateful.DefaultStateful;
import tripleo.elijah.stateful.State;
import tripleo.elijah.util.Maybe;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.Operation2;

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;

public class CR_FindCIs extends DefaultStateful implements CR_Action {
	private final @NotNull List<CompilerInput> inputs;
	private final @NotNull CCI                 cci;
	private final @NotNull IProgressSink       _ps;

	public CR_FindCIs(final @NotNull CompilerBeginning beginning) {
		State st = CompilationRunner.ST.INITIAL; // que?? 07/01

		inputs = beginning.compilerInput();

		var comp = beginning.compilation();
		var progressSink = beginning.progressSink();

		// TODO 09/05 look at 2 different progressSinks
		cci = new DefaultCCI(comp, comp._cis(), progressSink);
		_ps = comp.getCompilationEnclosure().getCompilationBus().defaultProgressSink();
	}

	CI_Crap crap = new CI_Crap();

	@Override
	public @NotNull Operation<Ok> execute(final @NotNull CR_State st, final @NotNull CB_Output aO) {
		final List<CompilerInput> x = make_crap_list(st);

		//for (final CompilerInput compilerInput : x) {
		//	cci.accept(compilerInput.acceptance_ci(), _ps);
		//}

		for (CompilerInput compilerInput : inputs) {
			final List<Operation2<CompilerInstructions>> directoryResults = compilerInput.getDirectoryResults();

			if (directoryResults != null) {
				if (directoryResults.size() > 0) {
					for (Operation2<CompilerInstructions> directoryResult : directoryResults) {
						if (directoryResult.mode() == Mode.SUCCESS) {
							cci.accept(new Maybe<>(ILazyCompilerInstructions.of(directoryResult.success()), null), _ps);
						}
					}
				}
			}
		}

		return Operation.success(Ok.instance());
	}

	@Override
	public void attach(final @NotNull CompilationRunner cr) {
	}

	private List<CompilerInput> make_crap_list(final @NotNull CR_State st) {
		final Compilation c = st.ca().getCompilation();

		final @NotNull ErrSink errSink = c.getErrSink();
		for (final CompilerInput input : inputs) {
			_processInput(c, errSink, crap, input);
		}

		return crap.list();
	}

	private void _processInput(final @NotNull Compilation c,
							   final @NotNull ErrSink errSink,
							   final @NotNull CI_Crap x,
							   final @NotNull CompilerInput input) {
		switch (input.ty()) {
		case NULL -> {
		}
		case SOURCE_ROOT -> {
		}
		default -> {
			return;
		}
		}

		final String  file_name = input.getInp();
		final File    f         = new File(file_name);

		final CompilationClosure compilationClosure = c.getCompilationClosure();

		if (input.isEzFile()) {
			new CW_inputIsEzFile().apply(input, compilationClosure, x::add);
		} else {
			//errSink.reportError("9996 Not an .ez file "+file_name);
			if (f.isDirectory()) {
				new CW_inputIsDirectory().apply(input, compilationClosure, f, x::add);
			} else {
				final NotDirectoryException d = new NotDirectoryException(f.toString());
				errSink.reportError("9995 Not a directory " + f.getAbsolutePath());
			}
		}
	}

	class CI_Crap {
		private final List<CompilerInput> x = new ArrayList<>();

		public List<CompilerInput> list() {
			return x;
		}

		public void add(final CompilerInput aInp) {
			x.add(aInp);
		}
	}

	@Override
	public @NotNull String name() {
		return "find cis";
	}
}
