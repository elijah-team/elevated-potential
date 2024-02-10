package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.USE_Reasoning;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;
import tripleo.elijah_elevated.lcm.LCM_Event_RootCI;

public class CK_ProcessInitialAction implements CK_Action {
	private final CompilerInstructions rootCI;

	public CK_ProcessInitialAction(final CompilerInstructions aRootCI) {
		rootCI = aRootCI;
	}

	@Override
	public Operation<Ok> execute(final CK_StepsContext aStepsContext, final CK_Monitor aMonitor) {
		final CD_CRS_StepsContext context = (CD_CRS_StepsContext) aStepsContext;
		final CR_State            crState = context.getState();
		final LCM                 lcm     = crState.getCompilationEnclosure().getCompilation().lcm();

		try {
			_action(lcm);

			return Operation.success(Ok.instance());
		} catch (final Exception aE) {
			return Operation.failure(aE);
		}
	}

	private void _action(final LCM lcm) {
		final USE_Reasoning reasoning = USE_Reasonings.initial(this);
		rootCI.advise(reasoning);

		final LCM_Event_RootCI ler = LCM_Event_RootCI.instance();
		lcm.asv(rootCI, ler);
	}

	public @NotNull CompilerInstructions maybeFoundResult() {
		return rootCI;
	}
}
