package tripleo.elijah_elevated_durable.compilation_bus;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.percy.CN_CompilerInputWatcher;
import tripleo.elijah.util.Maybe;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_elevateder.comp.Compilation;
import tripleo.elijah_elevateder.nextgen.comp_model.CM_CompilerInput;
import tripleo.wrap.File;

import java.nio.file.NotDirectoryException;
import java.util.List;

public class CB_FindCIs implements CB_Action {
	private final EDL_CompilationRunner compilationRunner;
	private final List<CompilerInput>   _inputs;
	private final CB_Output           o;

	@Contract(pure = true)
	public CB_FindCIs(final EDL_CompilationRunner aCompilationRunner, final List<CompilerInput> aInputs) {
		compilationRunner = aCompilationRunner;
		_inputs           = aInputs;
		o                 = compilationRunner.getCompilationEnclosure().getCB_Output();
	}

	@Override
	public void execute(CB_Monitor aMonitor) {
//		final CK_Monitor       monitor11   = /*aMonitor;//*/compilationRunner.getCompilationEnclosure().getDefaultMonitor();
		final CR_State    st = compilationRunner.getCrState();
		final Compilation c  = (Compilation) st.ca().getCompilation();
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
		// FIXME 24/01/09 oop
		switch (input.ty()) {
		case NULL, SOURCE_ROOT -> {}
		default -> {return;}
		}

		final CM_CompilerInput   cm                 = ((EDL_Compilation) c.getCompilation()).get(input); // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
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
				final EDL_Compilation compilation = (EDL_Compilation) compilationClosure.getCompilation();

				// FIXME 24/01/09 Duplication alert??
				compilation.addCompilerInputWatcher(CB_FindCIs::__CN_CompilerInputWatcher__event);
				CW_inputIsDirectory.apply(input, compilationClosure, f);
			} else {
				final NotDirectoryException d = new NotDirectoryException(f.toString());
				aErrSink.reportError("9995 Not a directory " + f.getAbsolutePath());
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
