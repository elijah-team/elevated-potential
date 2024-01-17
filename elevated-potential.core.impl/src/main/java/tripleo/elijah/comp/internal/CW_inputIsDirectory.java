package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.i.CompilationClosure;
import tripleo.elijah.comp.queries.CompilerInstructions_Result;
import tripleo.elijah.comp.queries.QuerySearchEzFiles;
import tripleo.wrap.File;

public class CW_inputIsDirectory {
	public static void apply(final @NotNull CompilerInput input,
							 final @NotNull CompilationClosure cc,
							 final File ff) {
		final File                        directory = input.getFileForDirectory();
		final QuerySearchEzFiles          q         = new QuerySearchEzFiles(cc);
		final CompilerInstructions_Result loci      = q.process(directory);

		input.setDirectoryResults(loci);
	}
}
