package tripleo.elijah.comp.i;

import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.internal.DefaultCompilerController;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

import java.util.List;

public interface CompilerController {
	void _setInputs(Compilation aCompilation, List<CompilerInput> aInputs);

	void printUsage();

	Operation<Ok> processOptions();

	void runner();

	void runner(DefaultCompilerController.Con con);
}
