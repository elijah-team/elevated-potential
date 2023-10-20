package tripleo.elijah.comp.i;

import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.i.extra.*;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

import java.util.List;

public interface CompilerController {
	void _setInputs(Compilation0 aCompilation, List<CompilerInput> aInputs);

	void printUsage();

	Operation<Ok> processOptions();

	void runner();

	void runner(Con con);

	interface Con {
		ICompilationRunner newCompilationRunner(ICompilationAccess aCompilationAccess);
	}
}
