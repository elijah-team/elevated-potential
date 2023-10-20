package tripleo.elijah.comp.i.extra;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;

public interface ICompilationRunner {
	void start(CompilerInstructions aRootCI, @NotNull GPipelineAccess pa);
}
