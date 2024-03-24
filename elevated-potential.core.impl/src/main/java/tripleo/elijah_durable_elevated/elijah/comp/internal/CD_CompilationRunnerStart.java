package tripleo.elijah_durable_elevated.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.comp.i.CompilerDriven;

public interface CD_CompilationRunnerStart extends CompilerDriven {
	void start(final @NotNull CompilerInstructions aCompilerInstructions,
			   final @NotNull CR_State crState,
			   final @NotNull CB_Output out);
}
