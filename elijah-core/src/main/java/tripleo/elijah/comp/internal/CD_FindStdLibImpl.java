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
			final File local_stdlib = CY_FindPrelude.__local_prelude_file(aPreludeName);

















			if (local_stdlib.exists()) {
				try {
					final String name = local_stdlib.toString();

					final CK_SourceFile sourceFile2 = CK_SourceFileFactory.get(local_stdlib, CK_SourceFileFactory.K.SpecifiedEzFile);
					sourceFile2.associate(cc);
					final Operation<CompilerInstructions> oci       = sourceFile2.process_query();

					switch (oci.mode()) {
					case SUCCESS -> {
						cc.getCompilation().pushItem(oci.success());
						result = oci;
						break;
					}
					case FAILURE -> {
						throw new IllegalStateException("expecting failure mode here.");
					}
					}
					if (result == null) {
						result = Objects.requireNonNull(oci);
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
