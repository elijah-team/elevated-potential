package tripleo.elijah.comp.i;

import tripleo.elijah.comp.i.extra.ICompilationRunner;
import tripleo.elijah.g.*;
import tripleo.elijah.util.*;

public interface CompilerController {
	void setEnclosure(GCompilationEnclosure aCompilationEnclosure);

	void printUsage();

	Operation<Ok> processOptions();

	void runner();

	void runner(Con con);

	interface Con {
		ICompilationRunner newCompilationRunner(ICompilationAccess aCompilationAccess);
	}
}
