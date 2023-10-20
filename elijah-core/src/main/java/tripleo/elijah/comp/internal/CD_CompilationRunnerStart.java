package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.i.CompilerDriven;
import tripleo.elijah.comp.i.extra.CB_Output;

public interface CD_CompilationRunnerStart extends CompilerDriven {

	void start(final @NotNull CompilerInstructions aCompilerInstructions, final @NotNull CR_State crState,
			final @NotNull CB_Output out);
}
