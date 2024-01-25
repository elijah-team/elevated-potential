package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.CompilerInput;
//import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;
import tripleo.elijah.comp.i.CB_Action;
import tripleo.elijah.comp.i.CB_Monitor;
import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.comp.i.CompilationClosure;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.comp.i.ILazyCompilerInstructions;
import tripleo.elijah.comp.i.extra.ICompilationRunner;
import tripleo.elijah.comp.percy.CN_CompilerInputWatcher;
import tripleo.elijah.nextgen.comp_model.CM_CompilerInput;
import tripleo.elijah.util.Maybe;
import tripleo.wrap.File;

import java.nio.file.NotDirectoryException;
import java.util.List;

class CB_FindCIs implements CB_Action {
	private final List<CompilerInput> _inputs;
	private final CB_Output           o;
	private final CompilationClosure cc;

	@Contract(pure = true)
	public CB_FindCIs(final ICompilationRunner aCompilationRunner, final List<CompilerInput> aInputs) {
		_inputs = aInputs;
		o       = aCompilationRunner.getCompilationEnclosure().getCB_Output();
		cc      = aCompilationRunner.c().getCompilationClosure();
	}

	@Override
	public void execute(CB_Monitor aMonitor) {
		//final CompilationEnclosure ce      = (CompilationEnclosure) cc.getCompilation().getCompilationEnclosure();

		// cilly is this, aka use CIL
		//ce.getCompilerInput()
				_inputs
				.stream().filter(input -> input.ty() != CompilerInput.Ty.ARG) // skip if in #_processInput
				.forEach(input -> _processInput(cc, input));

		logProgress_Stating("outputString.size", "" + o.get().size());
		aMonitor.reportSuccess(this, o);
	}

	@Contract(pure = true)
	@Override
	public @NotNull String name() {
		return "FindCIs";
	}

	private void _processInput(final @NotNull CompilationClosure c,
							   final @NotNull CompilerInput input) {
		final ErrSink errSink = cc.errSink();

		// FIXME 24/01/09 oop
		switch (input.ty()) {
		case NULL, SOURCE_ROOT -> {}
		default -> {return;}
		}

		final CM_CompilerInput   cm                 = ((CompilationImpl) c.getCompilation()).modelFactory().getCompilerInput(input);
		final File               f                  = cm.fileOf();
		final CompilationClosure compilationClosure = c.getCompilation().getCompilationClosure();

		if (input.isEzFile()) {
			if (input.isNull()) {
				input.certifyRoot();
			}

			CW_inputIsEzFile.apply(input, compilationClosure);
		} else {
			// errSink.reportError("9996 Not an .ez file "+file_name);
			if (f.isDirectory()) {
				final CompilationImpl compilation = (CompilationImpl) compilationClosure.getCompilation();

				// FIXME 24/01/09 Duplication alert??
				compilation.addCompilerInputWatcher(CB_FindCIs::__CN_CompilerInputWatcher__event);
				CW_inputIsDirectory.apply(input, compilationClosure, f);
			} else {
				final NotDirectoryException d = new NotDirectoryException(f.toString());
				errSink.reportError("9995 Not a directory " + f.getAbsolutePath());
			}
		}
	}

	private static void __CN_CompilerInputWatcher__event(final CN_CompilerInputWatcher.e aEvent, final CompilerInput aCompilerInput, final Object aObject) {
		switch (aEvent) {
		case ACCEPT_CI -> {
			final Maybe<ILazyCompilerInstructions> mci = (Maybe<ILazyCompilerInstructions>) aObject;
			aCompilerInput.accept_ci(mci);
		}
		case IS_EZ -> {
			final CM_CompilerInput cm = (CM_CompilerInput) aObject;
			cm.onIsEz();
		}
		default -> {
			System.err.println("~~ [11/24 111] " + aEvent + " " + aCompilerInput);
		}
		}
	}

	private void logProgress_Stating(final String aSection, final String aStatement) {
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_3("** CB_FindCIs :: %s :: %s".formatted(aSection, aStatement));
	}
}
