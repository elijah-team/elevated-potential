package tripleo.elijah.comp.process;

import tripleo.elijah.comp.graph.i.CK_Action;
import tripleo.elijah.comp.graph.i.CK_Monitor;
import tripleo.elijah.comp.graph.i.CK_StepsContext;
import tripleo.elijah.comp.internal.CR_State;
import tripleo.elijah.comp.internal.CompilationRunner;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

public class CK_AlmostComplete implements CK_Action {
	@Override
	public Operation<Ok> execute(final CK_StepsContext context1, final CK_Monitor aMonitor) {
		final CD_CRS_StepsContext context           = (CD_CRS_StepsContext) context1;
		final CR_State            crState           = context.getState();
		final CompilationRunner   compilationRunner = crState.runner();

		compilationRunner._cis().almostComplete();
		return Operation.success(Ok.instance());
	}
}
