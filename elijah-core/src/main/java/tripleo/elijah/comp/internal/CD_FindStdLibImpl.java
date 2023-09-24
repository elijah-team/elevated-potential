package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.queries.*;
import tripleo.elijah.nextgen.query.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.util.*;
import java.util.function.*;

public class CD_FindStdLibImpl implements CD_FindStdLib {
	public @NotNull Operation<CompilerInstructions> _____findStdLib(final @NotNull String prelude_name,
			final @NotNull CompilationClosure cc, final @NotNull CompilationRunner cr) {

		var slr = cc.getCompilation().paths().stdlibRoot();
		var pl = slr.child("lib-" + prelude_name);
		var sle = pl.child("stdlib.ez");

		var local_stdlib_1 = sle.toFile();
		System.err.println("3939 " + local_stdlib_1);

		// TODO stdlib path here
		final File local_stdlib = new File("lib_elijjah/lib-" + prelude_name + "/stdlib.ez");

		Operation<CompilerInstructions> oci = null;
		if (local_stdlib.exists()) {
			try {
				final String name = local_stdlib.getName();

				// TODO really want EIT_Input or CK_SourceFile here 07/01
				final SourceFileParserParams          p          = new SourceFileParserParams(null, local_stdlib, name, cc);
				final CK_SourceFile                   sourceFile = CK_SourceFileFactory.get(p, cr);
				final Operation<CompilerInstructions> oci        = sourceFile.process_query();

				if (oci.mode() == Mode.SUCCESS) {
					cc.getCompilation().pushItem(oci.success());
					return oci;
				}
			} catch (final Exception e) {
				return Operation.failure(e);
			}
		}

		return Operation.failure(new Exception("No stdlib found"));
	}

	@Override
	public void findStdLib(final @NotNull CR_State crState, final @NotNull String aPreludeName,
			final @NotNull Consumer<Operation<CompilerInstructions>> coci) {
		try {
			final CompilationRunner compilationRunner = crState.runner();

			@NotNull
			final Operation<CompilerInstructions> oci = _____findStdLib(aPreludeName,
					compilationRunner._accessCompilation().getCompilationClosure(), compilationRunner);
			coci.accept(oci);
		} catch (Exception aE) {
			throw new RuntimeException(aE);
		}
	}
}
