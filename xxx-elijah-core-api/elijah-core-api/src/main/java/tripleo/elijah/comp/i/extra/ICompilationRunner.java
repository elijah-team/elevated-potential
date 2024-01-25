package tripleo.elijah.comp.i.extra;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.specs.EzCache;
import tripleo.elijah.g.GCR_State;
import tripleo.elijah.g.GCompilationEnclosure;
import tripleo.elijah.g.GPipelineAccess;

public interface ICompilationRunner {
	void start(CompilerInstructions aRootCI, @NotNull GPipelineAccess pa);

	GCompilationEnclosure getCompilationEnclosure();

	EzCache ezCache(); // getExt??

	Compilation0 c();

	void nextCi(CompilerInstructions aCi);

	GCR_State getCrState();
}
