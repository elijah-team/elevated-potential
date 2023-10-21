package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.graph.i.CK_Monitor;
import tripleo.elijah.comp.graph.i.CK_StepsContext;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.sense.*;
import tripleo.elijah.util.Stupidity;

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.List;

class CB_FindCIs implements CB_Action, Sensable {
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
		final CK_Monitor       monitor   = compilationRunner.getCompilationEnclosure().getDefaultMonitor();
		final CR_State         st        = compilationRunner.getCrState();
		final Compilation      c         = (Compilation) st.ca().getCompilation();
		final @NotNull ErrSink errSink   = c.getErrSink();
		final CK_StepsContext  context   = new CD_CRS_StepsContext(st, o);
		final SenseList        senseList = new SenseList();

		for (final CompilerInput input : c.getCompilationEnclosure().getCompilerInput()) {
			_processInput(c, errSink, senseList, input);
		}

		for (final SenseList.Sensible sensible : senseList) {
			logProgress_ofSensible(sensible);
//            sensible.checkDirectoryResults(cci, _ps);
		}

		logProgress_Stating("outputString.size", ""+o.get().size());

		Stupidity.println_out_3("** CB_FindCIs :: outputString.size :: " + o.get().size());

		for (final CB_OutputString outputString : o.get()) {
			// 08/13
			Stupidity.println_out_3("** CB_FindCIs :: outputString :: " + outputString.getText());
		}

		// TODO capture action outputs
		//  09/27 is that not being done above??
		aMonitor.reportSuccess(this, o);

		final CK_AlmostComplete almostComplete = new CK_AlmostComplete();
		almostComplete.execute(context, monitor);
	}

	@Contract(pure = true)
	@Override
	public @NotNull String name() {
		return "FindCIs";
	}

	private void _processInput(final @NotNull Compilation aCompilation,
							   final @NotNull ErrSink aErrSink,
							   final @NotNull SenseList aSenseList,
							   final @NotNull CompilerInput aCompilerInput) {
		switch (aCompilerInput.ty()) {
		case NULL -> {
		}
		case SOURCE_ROOT -> {
		}
		default -> {
			aSenseList.skip(aCompilerInput, SenseList.U.SKIP, this);
			return;
		}
		}

		final String             file_name          = aCompilerInput.getInp();
		final File               f                  = new File(file_name);
		final CompilationClosure compilationClosure = aCompilation.getCompilationClosure();

		if (aCompilerInput.isEzFile()) {
			if (aCompilerInput.isNull()) {
				aCompilerInput.certifyRoot();
			}

			CW_inputIsEzFile.apply(aCompilerInput, compilationClosure, inp -> aSenseList.add(inp, SenseList.U.ADD, () -> SenseIndex.senseIndex_CW_inputIsEzFile));
		} else {
			// aErrSink.reportError("9996 Not an .ez file "+file_name);
			if (f.isDirectory()) {
				CW_inputIsDirectory.apply(aCompilerInput, compilationClosure, aSenseList::add);
			} else {
				final NotDirectoryException d = new NotDirectoryException(f.toString());
				aErrSink.reportError("9995 Not a directory " + f.getAbsolutePath());
			}
		}
	}

	@Override
	public SenseIndex getSenseIndex() {
		return SenseIndex.senseIndex_CB_FindCIs;
	}
}
