package tripleo.elijah.g;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;

public interface GCompilationRunner {
	void start(CompilerInstructions aRootCI, @NotNull GPipelineAccess pa);
}
