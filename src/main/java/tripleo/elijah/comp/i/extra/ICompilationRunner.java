package tripleo.elijah.comp.i.extra;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.internal.CR_State;
import tripleo.elijah.comp.specs.EzCache;
import tripleo.elijah.g.GPipelineAccess;

public interface ICompilationRunner {
	void start(CompilerInstructions aRootCI, @NotNull GPipelineAccess pa);

	@Nullable CR_State getCrState();

	EzCache ezCache();

//	void pushNextCompilerInstructions(CompilerInstructions aCi);
}
