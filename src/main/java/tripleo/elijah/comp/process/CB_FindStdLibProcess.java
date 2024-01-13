package tripleo.elijah.comp.process;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.generated.CompilationAlways;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.ICompilationRunner;
import tripleo.elijah.comp.impl.SingleActionProcess;
import tripleo.elijah.comp.internal.CR_State;
import tripleo.elijah.comp.internal.USE_Reasonings;
import tripleo.elijah.comp.internal_move_soon.*;
import tripleo.elijah.util.*;

import java.util.*;

import static tripleo.elijah.util.Helpers.*;

public class CB_FindStdLibProcess implements CB_Process {
	private final CB_FindStdLibAction action;

	public CB_FindStdLibProcess(CompilationEnclosure ce, ICompilationRunner cr) {
		action = new CB_FindStdLibAction(ce, cr);
	}

	@Override
	public List<CB_Action> steps() {
		return List_of(action);
	}

	@Override
	public String name() {
		return "CB_FindStdLibProcess";
	}

	class CB_FindStdLibAction implements CB_Action {
		private final     CompilationEnclosure  ce;
		private final CR_State crState;
		private final     List<CB_OutputString> o = new ArrayList<>(); // FIXME 07/01 how is this modified?
		private @Nullable CD_FindStdLib         findStdLib;

		public CB_FindStdLibAction(final CompilationEnclosure aCe, final @NotNull ICompilationRunner aCr) {
			ce      = aCe;
			crState = aCr.getCrState();

			obtain(); // TODO 09/08 Make this more complicated
		}

		@Override
		public void execute(CB_Monitor aMonitor) {
			if (findStdLib != null) {
				final String preludeName = CompilationAlways.defaultPrelude();
				findStdLib.findStdLib(crState, preludeName, this::getPushItem);

				final CB_Output o = ce.getCB_Output();
				aMonitor.reportSuccess(this, o);
			}
		}

		private void getPushItem(final @NotNull Operation2<CompilerInstructions> oci) {
			if (oci.mode() == Mode.SUCCESS) {
				final Compilation         c                    = ce.getCompilation();
				final CompilerInstructions compilerInstructions = oci.success();

				c.use(compilerInstructions, USE_Reasonings.findStdLib(findStdLib));
			} else {
				//throw new IllegalStateException();//oci.failure());
				System.err.println("6363 "+oci.failure().get());
			}
		}

		@Contract(pure = true)
		@Override
		public @NotNull String name() {
			return "find std lib";
		}

		private void obtain() {
			final Operation<CompilerDriven> x = ce.getCompilationDriver()
					.get(CompilationAlways.Tokens.COMPILATION_RUNNER_FIND_STDLIB2);

			if (x.mode() == Mode.SUCCESS) {
				findStdLib = (CD_FindStdLib) x.success();
			}
		}

		@Contract(value = " -> new", pure = true)
		public @NotNull CB_Process process() {
			return new SingleActionProcess(this, CB_FindStdLibProcess.this.name());
		}
	}
}
