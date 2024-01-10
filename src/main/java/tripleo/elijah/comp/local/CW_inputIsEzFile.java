package tripleo.elijah.comp.local;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.i.CompilationClosure;
import tripleo.elijah.comp.i.ILazyCompilerInstructions;
import tripleo.elijah.comp.impl.ILazyCompilerInstructions_;
import tripleo.elijah.util.Maybe;

public class CW_inputIsEzFile {
	public static void apply(final @NotNull CompilerInput input,
							 final @NotNull CompilationClosure cc) {
		final ILazyCompilerInstructions        ilci = ILazyCompilerInstructions_.of(input, cc);
		final Maybe<ILazyCompilerInstructions> m4   = Maybe.of(ilci);
		input.accept_ci(m4);
	}
}
