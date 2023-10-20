package tripleo.elijah.comp.nextgen;

import tripleo.elijah.comp.graph.i.*;

public class CK_DefaultStepRunner {
	public static void runStepsNow(final CK_Steps aSteps, final CK_StepsContext aStepsContext, final CK_Monitor aMonitor) {
		// TODO maybe not here
		//aStepsContext.begin();

		for (CK_Action step : aSteps.steps()) {
			step.execute(aStepsContext, aMonitor);
		}
	}
}
