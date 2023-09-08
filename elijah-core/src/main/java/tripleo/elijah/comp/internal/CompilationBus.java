package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.util.Stupidity;

import java.util.ArrayList;
import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

public class CompilationBus implements ICompilationBus {
	public final @NotNull  CompilerDriver   cd;
	private final @NotNull Compilation      c;
	private final @NotNull List<CB_Process> _processes           = new ArrayList<>();
	private final @NotNull IProgressSink    _defaultProgressSink = new IProgressSink() {
		@Override
		public void note(final Codes aCode, final @NotNull ProgressSinkComponent aProgressSinkComponent, final int aType, final Object[] aParams) {
			Stupidity.println_err_2(aProgressSinkComponent.printErr(aCode, aType, aParams));
		}
	};
	public                 CB_FindCIs              cb_findCIs;

	public CompilationBus(final @NotNull CompilationEnclosure ace) {
		c  = ace.getCompilationAccess().getCompilation();
		cd = new CompilerDriver(this);

		ace.setCompilerDriver(cd);
	}

	@Override
	public void add(final @NotNull CB_Action action) {
		_processes.add(new SingleActionProcess(action));
	}

	@Override
	public IProgressSink defaultProgressSink() {
		return _defaultProgressSink;
	}

	@Override
	public CompilerDriver getCompilerDriver() {
		return cd;
	}

	@Override
	public void add(final @NotNull CB_Process aProcess) {
		_processes.add(aProcess);
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

			@Override
			public void reportFailure(final @NotNull CB_Action aCBAction, final @NotNull CB_Output aCB_output) {
				System.err.println("FAILURE " + aCBAction.name() + " " + aCB_output.get());
			}

			@Override
			public void reportSuccess(final @NotNull CB_Action aCBAction, final @NotNull CB_Output aCB_output) {
				System.err.println("SUCCESS " + aCBAction.name() + " " + aCB_output.get());
			}
		};

		while (size < _processes.size()) {
			for (int i = size; i < _processes.size(); i++) {
				final CB_Process process = _processes.get(i);

				process.steps().stream().forEach(aCBAction -> aCBAction.execute(monitor));
			}

			size = _processes.size();
		}
		assert _processes.size() == size;
	}

	static class SingleActionProcess implements CB_Process {
		private final CB_Action a;

		public SingleActionProcess(final CB_Action aAction) {
			a = aAction;
		}

		@Override
		public @NotNull List<CB_Action> steps() {
			return List_of(a);
		}
	}
}
