package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.nextgen.impl.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.util.*;
import java.util.function.*;

public class CD_FindStdLibImpl implements CD_FindStdLib {

	@Override
	public void findStdLib(final @NotNull CR_State crState,
	                       final @NotNull String aPreludeName,
	                       final @NotNull Consumer<Operation<CompilerInstructions>> coci) {
		final CompilationRunner           compilationRunner = crState.runner();
		final @NotNull CompilationClosure cc                = compilationRunner._accessCompilation().getCompilationClosure();
		var                               slr               = cc.getCompilation().paths().stdlibRoot();
		var                               pl                = slr.child("lib-" + aPreludeName);
		var                               sle               = pl.child("stdlib.ez");

		try {
			@NotNull Operation<CompilerInstructions> result = null;

			final File local_stdlib_1 = sle.toFile();









			System.err.println("3939 " + local_stdlib_1);

			// TODO stdlib path here
			final File local_stdlib = new File("lib_elijjah/lib-" + aPreludeName + "/stdlib.ez");

















			if (local_stdlib.exists()) {
				try {
					final String name = local_stdlib.toString();

					final SourceFileParserParams          p          = new SourceFileParserParams(null, local_stdlib, name, cc);
					final CK_SourceFile                   sourceFile = CK_SourceFileFactory.get(p, compilationRunner);
					final Operation<CompilerInstructions> oci1       = sourceFile.process_query();

					switch (oci1.mode()) {
					case SUCCESS -> {
						cc.getCompilation().pushItem(oci1.success());
						result = oci1;
						break;
					}
					case FAILURE -> {
						throw new IllegalStateException("expecting failure mode here.");
					}
					}
					if (result == null) {
						result = Objects.requireNonNull(oci1);
					}

				} catch (final Exception e) {
					result = Operation.failure(e);
				}
			}
			if (result == null) {
				result = Operation.failure(new Exception("No stdlib found"));
			}

			@NotNull final Operation<CompilerInstructions> oci = result;
			coci.accept(oci);
		} catch (Exception aE) {
			throw new RuntimeException(aE);
		}
	}
}
