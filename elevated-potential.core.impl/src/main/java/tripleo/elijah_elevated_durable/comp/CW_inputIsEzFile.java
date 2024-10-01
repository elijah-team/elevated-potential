package tripleo.elijah_elevated_durable.comp;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.i.CompilationClosure;
import tripleo.elijah.comp.i.ILazyCompilerInstructions;
import tripleo.elijah_fluffy.util.Maybe;
import tripleo.elijah_elevateder.comp.internal.ILazyCompilerInstructions_;

public class CW_inputIsEzFile {
	public static void apply(final @NotNull CompilerInput input,
							 final @NotNull CompilationClosure cc) {
		final ILazyCompilerInstructions        ilci = ILazyCompilerInstructions_.of(input, cc);
		final Maybe<ILazyCompilerInstructions> m4   = Maybe.of(ilci);
		input.accept_ci(m4);
	}
}
