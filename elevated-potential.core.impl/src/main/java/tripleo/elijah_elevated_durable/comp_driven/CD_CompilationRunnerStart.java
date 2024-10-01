package tripleo.elijah_elevated_durable.comp_driven;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.comp.i.CompilerDriven;
import tripleo.elijah_elevated_durable.comp.CR_State;

public interface CD_CompilationRunnerStart extends CompilerDriven {
	void start(final @NotNull CompilerInstructions aCompilerInstructions,
			   final @NotNull CR_State crState,
			   final @NotNull CB_Output out);
}
