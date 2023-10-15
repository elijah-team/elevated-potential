package tripleo.elijah.comp.graph.i;

import tripleo.elijah.util.*;

import java.util.*;

public interface CK_Steps {
	interface CK_Monitor {
		void reportSuccess();
		void reportFailure();
	}

	interface CK_Action {
		Operation<Ok> execute(CK_Monitor aMonitor); // OutputStrings, Diagnostics, etc...
	}

	List<CK_Action> steps();
}
