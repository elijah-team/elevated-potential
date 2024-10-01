package tripleo.elijah.fluffy;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah_fluffy.util.Eventual;
import tripleo.elijah_fluffy.util.EventualRegister;
import tripleo.elijah_elevated_durable.lang_impl.OS_ModuleImpl;

public interface FluffyComp extends EventualRegister {
	void find_multiple_items(final @NotNull OS_Module aModule, OS_ModuleImpl.Complaint aC);

	FluffyModule module(OS_Module aModule);

	@Override
	void checkFinishEventuals();

	@Override
	<P> void register(Eventual<P> e);
}
