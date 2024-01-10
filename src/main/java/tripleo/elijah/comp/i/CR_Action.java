package tripleo.elijah.comp.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.comp.internal.CR_State;
import tripleo.elijah.comp.internal.CompilationRunner;
import tripleo.elijah.util.*;

public interface CR_Action {
	void attach(@NotNull CompilationRunner cr);

	@NotNull
	Operation<Ok> execute(@NotNull CR_State st, CB_Output aO);

	String name();
}
