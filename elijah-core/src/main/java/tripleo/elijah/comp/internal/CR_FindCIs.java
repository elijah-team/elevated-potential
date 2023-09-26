package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.sense.*;
import tripleo.elijah.stateful.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CR_FindCIs extends DefaultStateful implements CR_Action, Sensable {
	private final @NotNull List<CompilerInput> inputs;
	private final @NotNull CCI cci;
	private final @NotNull IProgressSink _ps;
	SenseList senseList = new SenseList();

	public CR_FindCIs(final @NotNull CompilerBeginning beginning) {
		inputs = beginning.compilerInput();

		var comp = beginning.compilation();
		var progressSink = beginning.progressSink();

		// TODO 09/05 look at 2 different progressSinks
		cci = new DefaultCCI(comp, comp._cis(), progressSink);
//        _ps = comp.getCompilationEnclosure().getCompilationBus().defaultProgressSink();;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
		_ps = progressSink;

		if (comp.getCompilerInputListener() instanceof CCI_Acceptor__CompilerInputListener cci_listener) {
			cci_listener.set(cci, _ps);
		}
	}

	private void _processInput(final @NotNull Compilation c, final @NotNull ErrSink errSink, final @NotNull SenseList x,
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
		final File f = new File(file_name);

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
	public void attach(final @NotNull CompilationRunner cr) {
	}

	@Override
	public @NotNull Operation<Ok> execute(final @NotNull CR_State st, final @NotNull CB_Output aO) {
		final Compilation c = st.ca().getCompilation();
		final @NotNull ErrSink errSink = c.getErrSink();

		for (final CompilerInput input : inputs) {
			_processInput(c, errSink, senseList, input);
		}

//        for (final SenseList.Sensible sensible : senseList) {
//            sensible.checkDirectoryResults(cci, _ps);
//        }

		return Operation.success(Ok.instance());
	}

	@Override
	public SenseIndex index() {
		return SenseIndex.senseIndex_CR_FindCIs;
	}

	@Override
	public @NotNull String name() {
		return "find cis";
	}

}
