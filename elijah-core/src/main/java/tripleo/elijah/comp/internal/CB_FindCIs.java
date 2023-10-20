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
		final SenseList        senseList = new SenseList();
		final CK_Monitor       monitor   = compilationRunner.getCompilationEnclosure().getDefaultMonitor();
		final CK_StepsContext  context   = new CD_CRS_StepsContext(compilationRunner.getCrState(), o);
		final CR_State         st        = compilationRunner.getCrState();
		final Compilation      c         = (Compilation) st.ca().getCompilation();
		final @NotNull ErrSink errSink   = c.getErrSink();

		for (final CompilerInput input : c.getCompilationEnclosure().getCompilerInput()) {
			_processInput(c, errSink, senseList, input);
		}

		for (final SenseList.Sensible sensible : senseList) {
			Stupidity.println_out_3("8989 " + sensible.toString());
//            sensible.checkDirectoryResults(cci, _ps);
		}

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


	private void _processInput(final @NotNull Compilation c,
							   final @NotNull ErrSink errSink,
							   final @NotNull SenseList x,
							   final @NotNull CompilerInput input) {
		switch (input.ty()) {
		case NULL -> {
		}
		case SOURCE_ROOT -> {
		}
		default -> {
			x.skip(input, SenseList.U.SKIP, this);
			return;
		}
		}

		final String file_name = input.getInp();
		final File   f         = new File(file_name);

		final CompilationClosure compilationClosure = c.getCompilationClosure();

		if (input.isEzFile()) {
			if (input.isNull()) {
				input.certifyRoot();
			}

			final CW_inputIsEzFile cw = new CW_inputIsEzFile();

			cw.apply(input, compilationClosure, inp -> x.add(inp, SenseList.U.ADD, cw));
		} else {
			// errSink.reportError("9996 Not an .ez file "+file_name);
			if (f.isDirectory()) {
				CW_inputIsDirectory.apply(input, compilationClosure, x::add);
			} else {
				final NotDirectoryException d = new NotDirectoryException(f.toString());
				errSink.reportError("9995 Not a directory " + f.getAbsolutePath());
			}
		}
	}

	@Override
	public SenseIndex index() {
		return SenseIndex.senseIndex_CB_FindCIs;
	}
}
