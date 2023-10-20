package tripleo.elijah.comp.internal;

import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

public class CK_ProcessInitialAction implements CK_Action {

	private final CompilerInstructions rootCI;

	public CK_ProcessInitialAction(final CompilerInstructions aRootCI) {
		rootCI = aRootCI;
	}

	@Override
	public Operation<Ok> execute(final CK_StepsContext context1, final CK_Monitor aMonitor) {

		final CD_CRS_StepsContext context = (CD_CRS_StepsContext) context1;
		final CR_State            crState = context.getState();
		final CB_Output           output  = context.getOutput();

		final CompilationRunner compilationRunner = crState.runner();

		try {
			compilationRunner._accessCompilation().use(rootCI, USE.USE_Reasonings.initial(Triple.of(this, compilationRunner, output)));
			return Operation.success(Ok.instance());
		} catch (final Exception aE) {
			return Operation.failure(aE);
		}
	}

	//@Override
	//public @NotNull String name() {
	//	return "process initial";
	//}

	public @NotNull CompilerInstructions maybeFoundResult() {
		return rootCI;
	}

}
