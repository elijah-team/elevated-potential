package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;

import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.util.*;

public class CW_inputIsEzFile {
	public static void apply(final @NotNull CompilerInput input,
			final @NotNull CompilationClosure cc) {
		final ILazyCompilerInstructions        ilci = ILazyCompilerInstructions_.of(input, cc);
		final Maybe<ILazyCompilerInstructions> m4   = Maybe.of(ilci);
		input.accept_ci(m4);
	}
}
