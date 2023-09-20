package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.sense.*;
import tripleo.elijah.util.*;

import java.util.function.*;

public class CW_inputIsEzFile implements Sensable {
	public void apply(final @NotNull CompilerInput input,
					  final @NotNull CompilationClosure cc,
					  final @NotNull Consumer<CompilerInput> x) {
		final ILazyCompilerInstructions ilci = ILazyCompilerInstructions.of(input, cc);

		final Maybe<ILazyCompilerInstructions> m4 = Maybe.of(ilci);
		input.accept_ci(m4);
		x.accept(input);
	}

	@Override
	public SenseIndex index() {
		return SenseIndex.senseIndex_CW_inputIsEzFile;
	}
}
