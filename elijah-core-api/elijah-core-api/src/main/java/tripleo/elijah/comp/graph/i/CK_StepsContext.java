package tripleo.elijah.comp.graph.i;

import tripleo.elijah.comp.i.*;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.util.*;

public interface CK_StepsContext {
	void addOutputString(CB_OutputString os);

	void addDiagnostic(Diagnostic d);

	Operation<Ok> getExecutionResult();

	void begin();
}
