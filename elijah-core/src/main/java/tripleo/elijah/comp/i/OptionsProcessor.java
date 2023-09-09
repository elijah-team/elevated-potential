package tripleo.elijah.comp.i;

import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.Operation2;

import java.util.List;

@FunctionalInterface
public interface OptionsProcessor {
	Operation<Ok> process(Compilation aC, List<CompilerInput> aInputs, ICompilationBus aCb);
}
