package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.percy.CN_CompilerInputWatcher;
import tripleo.elijah.nextgen.comp_model.CM_CompilerInput;
import tripleo.elijah.util.Maybe;

import java.nio.file.NotDirectoryException;
import java.util.List;

import tripleo.wrap.File;

class CB_FindCIs implements CB_Action {
	private final CompilationRunner   compilationRunner;
	private final List<CompilerInput> _inputs;
	private final CB_Output           o;

	@Contract(pure = true)
	public CB_FindCIs(final CompilationRunner aCompilationRunner, final List<CompilerInput> aInputs) {
		compilationRunner = aCompilationRunner;
		_inputs           = aInputs;
		o                 = compilationRunner.getCompilationEnclosure().getCB_Output();
	}

	@Override
	public void execute(CB_Monitor aMonitor) {
//		final CK_Monitor       monitor11   = /*aMonitor;//*/compilationRunner.getCompilationEnclosure().getDefaultMonitor();
		final CR_State         st      = compilationRunner.getCrState();
		final Compilation      c       = (Compilation) st.ca().getCompilation();
		final @NotNull ErrSink errSink = c.getErrSink();
//		final CK_StepsContext  context   = new CD_CRS_StepsContext(st, o);

		for (final CompilerInput input : c.getCompilationEnclosure().getCompilerInput()) {
			_processInput(c.getCompilationClosure(), errSink, input);
		}

		logProgress_Stating("outputString.size", "" + o.get().size());

		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_3("** CB_FindCIs :: outputString.size :: " + o.get().size());

		for (final CB_OutputString outputString : o.get()) {
			// 08/13
			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_3("** CB_FindCIs :: outputString :: " + outputString.getText());
		}

		// TODO capture action outputs
		//  09/27 is that not being done above??
		aMonitor.reportSuccess(this, o);

		//final CK_AlmostComplete almostComplete = new CK_AlmostComplete();
		//almostComplete.execute(context, monitor);
	}

	@Contract(pure = true)
	@Override
	public @NotNull String name() {
		return "FindCIs";
	}

	private void _processInput(final @NotNull CompilationClosure c,
							   final @NotNull ErrSink aErrSink,
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

		final CM_CompilerInput   cm                 = ((CompilationImpl) c.getCompilation()).get(input); // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
		final File               f                  = cm.fileOf();
		final CompilationClosure compilationClosure = c.getCompilation().getCompilationClosure();

		if (input.isEzFile()) {
			if (input.isNull()) {
				input.certifyRoot();
			}

			CW_inputIsEzFile.apply(input, compilationClosure);
		} else {
			// aErrSink.reportError("9996 Not an .ez file "+file_name);
			if (f.isDirectory()) {
				_inputIsDirectory(compilationClosure, input, f);
			} else {
				final NotDirectoryException d = new NotDirectoryException(f.toString());
				aErrSink.reportError("9995 Not a directory " + f.getAbsolutePath());
			}
		}
	}

	private void _inputIsDirectory(final @NotNull CompilationClosure c,
								   final @NotNull CompilerInput input,
								   final @NotNull File f) {
		((CompilationImpl)c.getCompilation()).addCompilerInputWatcher(new CN_CompilerInputWatcher() {
			@Override
			public void event(final e aEvent, final CompilerInput aCompilerInput, final Object aObject) {
				switch (aEvent) {
				case ACCEPT_CI -> {
					final Maybe<ILazyCompilerInstructions> mci = (Maybe<ILazyCompilerInstructions>) aObject;
					input.accept_ci(mci);
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
		});
		CW_inputIsDirectory.apply(input, c, f);
	}


	private void logProgress_Stating(final String aSection, final String aStatement) {
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_3("** CB_FindCIs :: %s :: %s".formatted(aSection, aStatement));
	}
}
