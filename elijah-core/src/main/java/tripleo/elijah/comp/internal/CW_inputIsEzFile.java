package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.i.CompilationClosure;
import tripleo.elijah.comp.i.ILazyCompilerInstructions;
import tripleo.elijah.util.Maybe;

import java.util.function.Consumer;

public class CW_inputIsEzFile {
	public void apply(final @NotNull CompilerInput input,
					  final @NotNull CompilationClosure cc,
					  final @NotNull Consumer<CompilerInput> x) {
		final ILazyCompilerInstructions ilci = ILazyCompilerInstructions.of(input, cc);

		final Maybe<ILazyCompilerInstructions> m4 = new Maybe<>(ilci, null);
		input.accept_ci(m4);
		x.accept(input);
	}
}
