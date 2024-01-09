package tripleo.elijah.comp.nextgen.i;

import tripleo.elijah.comp.graph.i.*;

public interface Asseveration {
	Object target();

	Asseverate code();

	void onLogProgress(Asseverable ce);

}
