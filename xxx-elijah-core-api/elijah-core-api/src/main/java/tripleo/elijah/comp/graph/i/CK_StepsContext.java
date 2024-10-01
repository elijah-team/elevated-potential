package tripleo.elijah.comp.graph.i;

import tripleo.elijah.comp.i.*;
import tripleo.elijah_fluffy.diagnostic.Diagnostic;
import tripleo.elijah_fluffy.util.Ok;
import tripleo.elijah_fluffy.util.Operation;

public interface CK_StepsContext {
	void addOutputString(CB_OutputString os);

	void addDiagnostic(Diagnostic d);

	Operation<Ok> getExecutionResult();

	void begin();
}
