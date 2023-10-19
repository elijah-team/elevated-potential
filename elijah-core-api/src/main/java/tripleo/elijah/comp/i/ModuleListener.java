package tripleo.elijah.comp.i;

import tripleo.elijah.world.i.*;

public interface ModuleListener {
	void close();

	void listen(WorldModule module);
}
