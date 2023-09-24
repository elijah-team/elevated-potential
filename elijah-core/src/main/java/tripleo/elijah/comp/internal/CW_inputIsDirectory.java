package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.queries.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.util.*;
import java.util.function.*;

public class CW_inputIsDirectory {
	public static void apply(final @NotNull CompilerInput input,
	                         final @NotNull CompilationClosure cc,
	                         final @NotNull Consumer<CompilerInput> x) {
		final String file_name = input.getInp();
		final File   directory = new File(file_name);

		input.setDirectory(directory);

		final QuerySearchEzFiles                     q    = new QuerySearchEzFiles(cc);
		final List<Operation2<CompilerInstructions>> loci = q.process2(directory);

		input.setDirectoryResults(loci);
	}
}
