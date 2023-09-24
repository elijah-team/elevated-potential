package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.nextgen.query.*;
import tripleo.elijah.util.*;

import java.util.*;

import static tripleo.elijah.util.Helpers.*;

public class CD_CompilationRunnerStart_1 implements CD_CompilationRunnerStart {

	protected void ___start(final @NotNull CR_State crState,
							final @NotNull CompilerBeginning beginning,
							final @NotNull CB_Output out) {
		if (crState.started) {
			boolean should_never_happen = false; // :grin:
			//return;
			assert should_never_happen;
		} else {
			crState.started = true;
		}

		final CR_FindCIs f1 = crState.runner().cr_find_cis();
		final CR_ProcessInitialAction f2 = new CR_ProcessInitialAction(beginning);
		final CR_AlmostComplete       f3 = crState.runner().cr_AlmostComplete();
		final CR_RunBetterAction      f4 = new CR_RunBetterAction();

		final @NotNull List<CR_Action> crActionList = List_of(/*f1,*/ f2, f3, f4);
		final @NotNull List<Operation<Ok>> crActionResultList = new ArrayList<>(crActionList.size());

		for (final CR_Action each : crActionList) {
			each.attach(crState.runner());
			var res = each.execute(crState, out);
			crActionResultList.add(res);
		}

		// TODO execute should do this automatically
		for (int i = 0; i < crActionResultList.size(); i++) {
			var                      action           = crActionList.get(i);
			final Operation<Ok> booleanOperation = crActionResultList.get(i);

			final String s = "%s %b".formatted(action.name(), (booleanOperation.mode() == Mode.SUCCESS));
			out.logProgress(5959, s);
		}

		NotImplementedException.raise_stop();
	}

	@Override
	public void start(final @NotNull CompilerInstructions aRootCI,
					  final @NotNull CR_State crState,
					  final @NotNull CB_Output out) {
		final @NotNull CompilationRunner             cr  = crState.runner();
		var                                          ce  = crState.ca.getCompilation().getCompilationEnclosure();
		var                                          ci  = ce.getCompilerInput();
		final @NotNull IPipelineAccess               pa  = crState.ca.getCompilation().getCompilationEnclosure().getPipelineAccess();
		final @NotNull Compilation.CompilationConfig cfg = crState.ca.getCompilation().cfg();

		final List<CompilerInput> compilerInput = pa.getCompilerInput();

		assert compilerInput == ci;

		final CompilerBeginning beginning = new CompilerBeginning(cr._accessCompilation(), aRootCI, compilerInput, cr.getProgressSink(), cfg);

		___start(crState, beginning, out);
	}
}
