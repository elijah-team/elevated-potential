package tripleo.elijah.comp.nextgen.impl;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.nextgen.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.nextgen.query.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.util.*;

public class CK_SourceFile__SpecifiedEzFile extends __CK_SourceFile__AbstractEzFile {
	private final File file;

	public CK_SourceFile__SpecifiedEzFile(final File aFile) {
		file = aFile;
	}

	@Override
	public Operation<CompilerInstructions> process_query() {
		final Operation<CompilerInstructions> oci = process_query(compilation.getIO(), compilation.getCompilationEnclosure().getCompilationRunner().ezCache());

		super.asserverate();

		return oci;
	}

	private Operation<CompilerInstructions> process_query(final IO io, final @NotNull EzCache ezCache) {
		var ezSpec = new EzSpec(
				file_name(),
				() -> {
					try {
						return io.readFile(file);
					} catch (FileNotFoundException aE) {
						throw new RuntimeException(aE);
					}
				},
				file
		);

		return realParseEzFile(ezSpec, ezCache);
	}

	private String file_name() {
		return this.file.getName();
	}

}
