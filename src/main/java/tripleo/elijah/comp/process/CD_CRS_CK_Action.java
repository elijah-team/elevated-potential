package tripleo.elijah.comp.process;

import tripleo.elijah.comp.graph.i.CK_Action;
import tripleo.elijah.comp.graph.i.CK_Monitor;
import tripleo.elijah.comp.graph.i.CK_StepsContext;
import tripleo.elijah.comp.i.CR_Action;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

class CD_CRS_CK_Action implements CK_Action {
	private final CD_CompilationRunnerStart_1 CDCompilationRunnerStart1;
	private final CR_Action action;

	public CD_CRS_CK_Action(final CD_CompilationRunnerStart_1 aCDCompilationRunnerStart1, final CR_Action aAction) {
		CDCompilationRunnerStart1 = aCDCompilationRunnerStart1;
		action                    = aAction;
	}

	@Override
	public Operation<Ok> execute(final CK_StepsContext context1, final CK_Monitor aMonitor) {
		final CD_CRS_StepsContext context = (CD_CRS_StepsContext) context1;

		final Operation<Ok> result = action.execute(context.getState(), context.getOutput());

		CDCompilationRunnerStart1.getCrActionResultList().add(result); // FIXME 10/20 associate result with action in list (steps)

		return result;
	}
}
