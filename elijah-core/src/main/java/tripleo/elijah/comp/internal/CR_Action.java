package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.extra.CB_Output;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

public interface CR_Action {
	void attach(@NotNull CompilationRunner cr);

	@NotNull
	Operation<Ok> execute(@NotNull CR_State st, CB_Output aO);

	String name();
}
