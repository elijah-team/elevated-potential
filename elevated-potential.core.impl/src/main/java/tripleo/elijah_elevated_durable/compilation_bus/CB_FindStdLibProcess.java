package tripleo.elijah_elevated_durable.compilation_bus;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;
import tripleo.elijah_elevated.comp.compilation_bus.SingleActionProcess;
import tripleo.elijah_elevated_durable.comp.CR_State;
import tripleo.elijah_elevated_durable.comp.EDL_Compilation;
import tripleo.elijah_elevated_durable.input.USE_Reasonings;
import tripleo.elijah_elevateder.comp.i.Compilation;
import tripleo.elijah_fluffy.util.*;

import java.util.ArrayList;
import java.util.List;

import static tripleo.elijah_fluffy.util.Helpers.List_of;

public class CB_FindStdLibProcess implements CB_Process {
	private final CB_FindStdLibAction action;

	public CB_FindStdLibProcess(CompilationEnclosure ce, EDL_CompilationRunner cr) {
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
		private final CompilationEnclosure  ce;
		private final CR_State              crState;
		private final List<CB_OutputString> o = new ArrayList<>(); // FIXME 07/01 how is this modified?
		private @Nullable CD_FindStdLib         findStdLib;

		public CB_FindStdLibAction(final CompilationEnclosure aCe, final @NotNull EDL_CompilationRunner aCr) {
			ce      = aCe;
			crState = aCr.getCrState();

			obtain(); // TODO 09/08 Make this more complicated
		}

		@Override
		public void execute(CB_Monitor aMonitor) {
			if (findStdLib != null) {
				final String preludeName = EDL_Compilation.CompilationAlways.defaultPrelude();
				findStdLib.findStdLib(crState, preludeName, this::getPushItem);

				final CB_Output o = ce.getCB_Output();
				aMonitor.reportSuccess(this, o);
			}
		}

		private void getPushItem(final @NotNull Operation2<CompilerInstructions> oci) {
			if (oci.mode() == Mode.SUCCESS) {
				final Compilation          c                    = ce.getCompilation();
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
					.get(EDL_Compilation.CompilationAlways.Tokens.COMPILATION_RUNNER_FIND_STDLIB2);

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
