package tripleo.elijah.comp.i;

import org.apache.commons.lang3.tuple.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.nextgen.impl.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.query.*;
import tripleo.elijah.util.*;

import java.io.*;

public interface ILazyCompilerInstructions {
	@Contract(value = "_, _ -> new", pure = true)
	static @NotNull ILazyCompilerInstructions of(final @NotNull CompilerInput input,
	                                             final @NotNull CompilationClosure cc) {
		final String file_name = input.getInp();
		final File   f         = new File(file_name);

		return new ILazyCompilerInstructions() {
			private Operation2<CompilerInstructions> operation;

			@Override
			public CompilerInstructions get() {
				// 1. Ask the factory
				// 2. "Associate" our givens
				// 3. Ask the source file to process
				// 4. Just return on success
				// 5. Return null for failure

				CK_SourceFile<CompilerInstructions> sf = CK_SourceFileFactory.get(f, CK_SourceFileFactory.K.SpecifiedEzFile);
				sf.associate(input, cc);
				operation = sf.process_query();

				if (operation.mode() == Mode.SUCCESS) {
					final CompilerInstructions parsed = operation.success();
					return parsed;
				}

				return null;
			}

			@Override
			public @Nullable Operation2<CompilerInstructions> getOperation() {
				return operation;
			}
		};
	}

	@Contract(value = "_ -> new", pure = true)
	static @NotNull ILazyCompilerInstructions of(final @NotNull CompilerInstructions aCompilerInstructions) {
		return new ILazyCompilerInstructions() {
			@Override
			public CompilerInstructions get() {
				return aCompilerInstructions;
			}

			@Override
			public @Nullable Operation2<CompilerInstructions> getOperation() {
				return null;
			}
		};
	}

	CompilerInstructions get();

	@Nullable Operation2<CompilerInstructions> getOperation();
}
