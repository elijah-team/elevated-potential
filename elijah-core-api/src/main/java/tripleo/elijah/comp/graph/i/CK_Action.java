package tripleo.elijah.comp.graph.i;

import tripleo.elijah.util.*;

public interface CK_Action {
	Operation<Ok> execute(CK_StepsContext context, CK_Monitor aMonitor); // OutputStrings, Diagnostics, etc...
}
