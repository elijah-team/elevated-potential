package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.*;

public interface CD_CompilationRunnerStart extends CompilerDriven {
	void start(final @NotNull CompilerInstructions aCompilerInstructions,
			final @NotNull CR_State crState,
			final @NotNull CB_Output out);
}
