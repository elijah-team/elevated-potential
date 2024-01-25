package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.comp.i.extra.ICompilationRunner;
import tripleo.elijah.util.*;

public interface CR_Action {
	void attach(@NotNull ICompilationRunner cr);

	@NotNull
	Operation<Ok> execute(@NotNull CR_State st, CB_Output aO);

	String name();
}
