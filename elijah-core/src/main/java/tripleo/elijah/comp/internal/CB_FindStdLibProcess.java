package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.nextgen.query.*;
import tripleo.elijah.util.*;

import java.util.*;

import static tripleo.elijah.util.Helpers.*;

public class CB_FindStdLibProcess implements CB_Process {
	private final CB_FindStdLibAction action;

	public CB_FindStdLibProcess(CompilationEnclosure ce, CompilationRunner cr) {
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
		private final     CR_State              crState;
		private final     List<CB_OutputString> o = new ArrayList<>(); // FIXME 07/01 how is this modified?
		private @Nullable CD_FindStdLib         findStdLib;

		public CB_FindStdLibAction(final CompilationEnclosure aCe, final @NotNull CompilationRunner aCr) {
			ce      = aCe;
			crState = aCr.getCrState();

			obtain(); // TODO 09/08 Make this more complicated
		}

		@Override
		public void execute(CB_Monitor aMonitor) {
			if (findStdLib != null) {
				final String preludeName = Compilation.CompilationAlways.defaultPrelude();
				findStdLib.findStdLib(crState, preludeName, this::getPushItem);

				final CB_Output o = ce.getCB_Output();
				aMonitor.reportSuccess(this, o);
			}
		}

		private void getPushItem(final @NotNull Operation<CompilerInstructions> oci) { // TODO reason
			if (oci.mode() == Mode.SUCCESS) {
				final Compilation c = ce.getCompilation();

				CompilerInstructions compilerInstructions = oci.success();

				c.pushItem(compilerInstructions);
				c.use(compilerInstructions, USE.USE_Reasonings.findStdLib(findStdLib));
			} else {
				throw new IllegalStateException(oci.failure());
			}
		}

		@Contract(pure = true)
		@Override
		public @NotNull String name() {
			return "find std lib";
		}

		private void obtain() {
			final Operation<CompilerDriven> x = ce.getCompilationDriver()
					.get(Compilation.CompilationAlways.Tokens.COMPILATION_RUNNER_FIND_STDLIB2);

			if (x.mode() == Mode.SUCCESS) {
				findStdLib = (CD_FindStdLib) x.success();
			}
		}

		@Contract(value = " -> new", pure = true)
		public @NotNull CB_Process process() {
			return new DefaultCompilationBus.SingleActionProcess(this);
		}
	}
}
