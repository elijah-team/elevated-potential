package tripleo.elijah.comp.i;

import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah_fluffy.util.Ok;
import tripleo.elijah_fluffy.util.Operation;

import java.util.List;

@FunctionalInterface
public interface OptionsProcessor {
	Operation<Ok> process(Compilation0 aC, List<CompilerInput> aInputs, ICompilationBus aCb);
}
