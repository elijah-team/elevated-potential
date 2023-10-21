package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.i.CompilationClosure;
import tripleo.elijah.comp.i.ILazyCompilerInstructions;
import tripleo.elijah.util.Maybe;

import java.util.function.Consumer;

public class CW_inputIsEzFile /*implements Sensable*/ {
	public static void apply(final @NotNull CompilerInput input,
							 final @NotNull CompilationClosure cc,
							 final @NotNull Consumer<CompilerInput> x) {
		final ILazyCompilerInstructions        ilci = ILazyCompilerInstructions_.of(input, cc);
		final Maybe<ILazyCompilerInstructions> m4   = Maybe.of(ilci);
		input.accept_ci(m4);
		x.accept(input);
	}

	//@Override
	//public SenseIndex getSenseIndex() {
	//	return SenseIndex.senseIndex_CW_inputIsEzFile;
	//}
}
