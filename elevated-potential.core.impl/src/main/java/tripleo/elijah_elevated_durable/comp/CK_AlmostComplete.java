package tripleo.elijah_elevated_durable.comp;

import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;
import tripleo.elijah_elevated_durable.comp_driven.CD_CRS_StepsContext;
import tripleo.elijah_elevated_durable.compilation_bus.EDL_CompilationRunner;

public class CK_AlmostComplete implements CK_Action {

	@Override
	public Operation<Ok> execute(final CK_StepsContext context1, final CK_Monitor aMonitor) {
		final CD_CRS_StepsContext   context           = (CD_CRS_StepsContext) context1;
		final CR_State              crState           = context.getState();
		final EDL_CompilationRunner compilationRunner = crState.runner();

		return compilationRunner._cis().almostComplete();
	}

}
