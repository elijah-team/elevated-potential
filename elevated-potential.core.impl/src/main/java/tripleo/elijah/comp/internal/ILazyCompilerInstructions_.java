package tripleo.elijah.comp.internal;

import java.io.*;

import org.jetbrains.annotations.*;

import tripleo.elijah.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.nextgen.impl.*;
import tripleo.elijah.util.*;

public abstract class ILazyCompilerInstructions_ {
	@Contract(value = "_, _, _ -> new", pure = true)
	public static void ofEventual(final @NotNull CompilerInput input,
			final @NotNull CompilationClosure cc,
								  final Eventual<CompilerInstructions> eilci) {
		final String file_name = input.getInp();
		final File   f         = new File(file_name);

		// 1. Ask the factory
		// 2. "Associate" our givens
		// 3. Ask the source file to process
		// 4. Just return on success
		// 5. Return null for failure

		CK_SourceFile<CompilerInstructions> sf = CK_SourceFileFactory.get(f, CK_SourceFileFactory.K.SpecifiedEzFile);
		sf.associate(input, cc);
		final Operation2<CompilerInstructions> operation = sf.process_query();

		if (operation.mode() == Mode.SUCCESS) {
			final CompilerInstructions parsed = operation.success();
			eilci.resolve(parsed);
		} else
			eilci.reject(operation.failure());
	}
	@Contract(value = "_, _ -> new", pure = true)
	public static @NotNull ILazyCompilerInstructions of(final @NotNull CompilerInput input,
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

				final CK_SourceFile<CompilerInstructions> sf = CK_SourceFileFactory.get(f, CK_SourceFileFactory.K.SpecifiedEzFile);
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
	public static @NotNull ILazyCompilerInstructions of(final @NotNull CompilerInstructions aCompilerInstructions) {
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
}
