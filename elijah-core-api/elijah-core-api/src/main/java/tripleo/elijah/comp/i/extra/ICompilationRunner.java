package tripleo.elijah.comp.i.extra;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.g.GPipelineAccess;

public interface ICompilationRunner {
	void start(CompilerInstructions aRootCI, @NotNull GPipelineAccess pa);
}
