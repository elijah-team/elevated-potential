package tripleo.elijah.stages.deduce.fluffy.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.impl.*;

public interface FluffyComp {
	void find_multiple_items(final @NotNull OS_Module aModule, OS_ModuleImpl.Complaint aC);

	FluffyModule module(OS_Module aModule);
}
