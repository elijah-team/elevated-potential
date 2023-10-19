package tripleo.elijah.comp.nextgen.i;

import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;

public interface Asseveration {
	Object target();

	Asseverate code();

	void onLogProgress(CompilationEnclosure ce);
}
