package tripleo.elijah.comp.graph.i;

import tripleo.elijah_fluffy.util.Ok;
import tripleo.elijah_fluffy.util.Operation;

public interface CK_Action {
	Operation<Ok> execute(CK_StepsContext context, CK_Monitor aMonitor); // OutputStrings, Diagnostics, etc...
}
