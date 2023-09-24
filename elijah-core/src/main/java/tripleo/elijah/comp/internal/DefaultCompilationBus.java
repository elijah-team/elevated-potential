package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.util.*;

import java.text.*;
import java.util.*;

import static tripleo.elijah.util.Helpers.*;

public class DefaultCompilationBus implements ICompilationBus {
	static class SingleActionProcess implements CB_Process {
		// README tape
		private final CB_Action a;

		public SingleActionProcess(final CB_Action aAction) {
			a = aAction;
		}

		@Override
		public @NotNull List<CB_Action> steps() {
			return List_of(a);
		}
	}
	@lombok.Getter
	private final @NotNull CompilerDriver   compilerDriver;
	private final @NotNull Compilation      c;
	private final @NotNull List<CB_Process> _processes           = new ArrayList<>();
	private final @NotNull IProgressSink    _defaultProgressSink = new IProgressSink() {
		@Override
		public void note(final Codes aCode, final @NotNull ProgressSinkComponent aProgressSinkComponent, final int aType, final Object[] aParams) {
			Stupidity.println_err_2(aProgressSinkComponent.printErr(aCode, aType, aParams));
		}
	};

	public                 CB_FindCIs       cb_findCIs;

	public DefaultCompilationBus(final @NotNull CompilationEnclosure ace) {
		c              = ace.getCompilationAccess().getCompilation();
		compilerDriver = new CompilerDriver(this);

		ace.setCompilerDriver(compilerDriver);
	}

	@Override
	public void add(final @NotNull CB_Action action) {
		_processes.add(new SingleActionProcess(action));
	}

	@Override
	public void add(final @NotNull CB_Process aProcess) {
		_processes.add(aProcess);
	}

	@Override
	public IProgressSink defaultProgressSink() {
		return _defaultProgressSink;
	}

	@Override
	public void inst(final @NotNull ILazyCompilerInstructions aLazyCompilerInstructions) {
		_defaultProgressSink.note(IProgressSink.Codes.LazyCompilerInstructions_inst, ProgressSinkComponent.CompilationBus_, -1, new Object[]{aLazyCompilerInstructions.get()});
	}

	@Override
	public void option(final @NotNull CompilationChange aChange) {
		aChange.apply(c);
	}

	@Override
	public List<CB_Process> processes() {
		return _processes;
	}

	public void runProcesses() {
		int size = 0;

		var monitor = new CB_Monitor() {

			// TODO/HACK queue then print after loop
			//   also send to UI (UT_Controller)

			void print(String s) {
				System.err.print(s);
			}

			void println() {
				System.err.println();
			}

			void println(String s) {
				System.err.println(s);
			}

			@Override
			public void reportFailure(final @NotNull CB_Action aCBAction, final @NotNull CB_Output aCB_output) {
				System.err.println("FAILURE " + aCBAction.name() + " " + aCB_output.get());
			}

			@Override
			public void reportSuccess(final @NotNull CB_Action aCBAction, final @NotNull CB_Output aCB_output) {
				final String header = "SUCCESS " + aCBAction.name();
				println(header);
				for (int i = 0; i < header.length() + 2; i++) {
					print("=");
				}
				println();

				final List<CB_OutputString> outputStrings = aCB_output.get();
				for (CB_OutputString outputString : outputStrings) {
					println(" " + outputString.getText());
				}
				println();
			}
		};

		while (size < _processes.size()) {
			int i;
			for (i = size; i < _processes.size(); i++) {
				final CB_Process process = _processes.get(i);

				if (DebugFlags._DefaultCompilationBus) {
					final String name;

					if (process instanceof SingleActionProcess sap) {
						name = sap.a.name();
					} else {
						name = process.getClass().getName();
					}

					System.err.println(MessageFormat.format("DefaultCompilationBus i={0} size={1} {2}", i, size, name));
				}

				for (CB_Action action : process.steps()) {
					action.execute(monitor);
				}
			}

			int old_size = size;
			size = _processes.size();
			if (DebugFlags._DefaultCompilationBus) {
				System.err.println(MessageFormat.format("DefaultCompilationBus reset size old_size={0} new_size={1} last={2}", old_size, size, i));
			}
		}
		assert _processes.size() == size;
	}
}
