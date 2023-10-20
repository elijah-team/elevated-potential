package tripleo.elijah.comp.internal;

import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

public class CR_ProcessInitialAction implements CR_Action {
	private final @NotNull CompilerInstructions rootCI;

	@Contract(pure = true)
	public CR_ProcessInitialAction(final @NotNull CompilerInstructions aRootCI) {
		rootCI = aRootCI;
	}

	@Override
	public void attach(final @NotNull CompilationRunner cr) {
	}

	@Override
	public @NotNull Operation<Ok> execute(final @NotNull CR_State st, final CB_Output aO) {
		CompilationRunner compilationRunner = st.runner();

		try {
			compilationRunner._accessCompilation().use(rootCI, USE.USE_Reasonings.initial(Triple.of(this, compilationRunner, aO)));
			return Operation.success(Ok.instance());
		} catch (final Exception aE) {
			return Operation.failure(aE);
		}
	}

	@Override
	public @NotNull String name() {
		return "process initial";
	}

	public @NotNull CompilerInstructions maybeFoundResult() {
		return rootCI;
	}
}
