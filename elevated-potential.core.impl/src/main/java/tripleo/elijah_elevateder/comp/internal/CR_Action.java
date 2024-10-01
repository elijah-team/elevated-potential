package tripleo.elijah_elevateder.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah_elevated_durable.comp.CR_State;
import tripleo.elijah_elevated_durable.compilation_bus.EDL_CompilationRunner;
import tripleo.elijah_fluffy.util.Ok;
import tripleo.elijah_fluffy.util.Operation;

public interface CR_Action {
	void attach(@NotNull EDL_CompilationRunner cr);

	@NotNull
	Operation<Ok> execute(@NotNull CR_State st, CB_Output aO);

	String name();
}
