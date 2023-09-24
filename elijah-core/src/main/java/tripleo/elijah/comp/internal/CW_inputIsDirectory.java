package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.i.CompilationClosure;
import tripleo.elijah.comp.queries.QuerySearchEzFiles;
import tripleo.elijah.util.Operation2;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

public class CW_inputIsDirectory {
	public void apply(final @NotNull CompilerInput input, final @NotNull CompilationClosure cc,
			final @NotNull File directory, final @NotNull Consumer<CompilerInput> x) {
		input.setDirectory(directory);

		final QuerySearchEzFiles q = new QuerySearchEzFiles(cc);
		final List<Operation2<CompilerInstructions>> loci = q.process2(directory);

		input.setDirectoryResults(loci);
	}
}
