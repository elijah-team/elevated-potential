package tripleo.elijah.comp.process;

import tripleo.elijah.ci.CompilerInstructions;

public interface CPX_Signals {
	void subscribeCalculateFinishParse(final CPX_CalculateFinishParse cp_OutputPath);
	void subscribeRunStepLoop(final CPX_RunStepLoop cp_RunStepLoop);

	void signalRunStepLoop(final CompilerInstructions aRoot);
}
