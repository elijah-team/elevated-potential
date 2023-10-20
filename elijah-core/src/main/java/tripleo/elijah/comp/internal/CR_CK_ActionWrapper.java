package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.graph.i.CK_Action;
import tripleo.elijah.comp.graph.i.CK_Monitor;
import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

public class CR_CK_ActionWrapper implements CR_Action {
	private final CD_CompilationRunnerStart_1 CDCompilationRunnerStart1;
	private final CK_Action                   action;
	private final CK_Monitor                  monitor;

	public CR_CK_ActionWrapper(final CD_CompilationRunnerStart_1 aCDCompilationRunnerStart1, final CK_Action aAction, final CK_Monitor aMonitor) {
		CDCompilationRunnerStart1 = aCDCompilationRunnerStart1;
		action                    = aAction;
		monitor                   = aMonitor;
	}

	@Override
	public void attach(@NotNull final CompilationRunner cr) {

	}

	@Override
	public @NotNull Operation<Ok> execute(@NotNull final CR_State st, final CB_Output aO) {
		return action.execute(CDCompilationRunnerStart1.stepContext, monitor);
	}

	@Override
	public String name() {
		return getClass().getName();
	}
}
