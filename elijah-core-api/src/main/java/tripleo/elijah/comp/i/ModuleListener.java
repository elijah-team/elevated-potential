package tripleo.elijah.comp.i;

import tripleo.elijah.g.*;

public interface ModuleListener {
	void close();

	void listen(GWorldModule module);
}
