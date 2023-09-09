package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.i.CR_Action;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

public class CR_ProcessInitialAction implements CR_Action {
	private final @NotNull CompilerInstructions rootCI;
	private final          boolean              do_out;

	@Contract(pure = true)
	public CR_ProcessInitialAction(final @NotNull CompilerBeginning beginning) {
		rootCI = beginning.compilerInstructions();
		do_out = beginning.cfg().do_out;
	}

	@Override
	public void attach(final @NotNull CompilationRunner cr) {
	}

	@Override
	public @NotNull Operation<Ok> execute(final @NotNull CR_State st, final CB_Output aO) {
		CompilationRunner compilationRunner = st.runner();

		try {
			compilationRunner._accessCompilation().use(rootCI, do_out);
			return Operation.success(Ok.instance());
		} catch (final Exception aE) {
			return Operation.failure(aE);
		}
	}

	@Override
	public @NotNull String name() {
		return "process initial";
	}
}
