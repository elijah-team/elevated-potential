package tripleo.elijah.stages.deduce.fluffy.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.Eventual;
import tripleo.elijah.EventualRegister;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.impl.OS_ModuleImpl;

public interface FluffyComp extends EventualRegister {
	void find_multiple_items(final @NotNull OS_Module aModule, OS_ModuleImpl.Complaint aC);

	FluffyModule module(OS_Module aModule);

	@Override
	void checkFinishEventuals();

	@Override
	<P> void register(Eventual<P> e);
}
