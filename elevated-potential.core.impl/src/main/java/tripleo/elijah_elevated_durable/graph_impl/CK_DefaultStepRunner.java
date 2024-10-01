package tripleo.elijah_elevated_durable.graph_impl;

import tripleo.elijah.comp.graph.i.*;

public class CK_DefaultStepRunner {
	public static void runStepsNow(final CK_Steps aSteps, final CK_StepsContext aStepsContext, final CK_Monitor aMonitor) {
		// TODO maybe not here
		//aStepsContext.begin();
		int y=2;

		for (CK_Action step : aSteps.steps()) {
			step.execute(aStepsContext, aMonitor);
		}
	}
}
