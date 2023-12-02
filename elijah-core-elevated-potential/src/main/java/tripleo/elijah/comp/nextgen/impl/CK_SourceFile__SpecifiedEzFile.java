package tripleo.elijah.comp.nextgen.impl;

import com.google.common.base.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.util.*;

import java.io.*;

public class CK_SourceFile__SpecifiedEzFile extends __CK_SourceFile__AbstractEzFile {
	private final File file;

	public CK_SourceFile__SpecifiedEzFile(final File aFile) {
		file = aFile;
	}

	@Override
	public Operation2<CompilerInstructions> process_query() {
		final Operation2<CompilerInstructions> oci = process_query(compilation.getIO(), compilation.getCompilationEnclosure().getCompilationRunner().ezCache());

		super.asserverate();

		return oci;
	}

	private Operation2<CompilerInstructions> process_query(final IO io, final @NotNull EzCache ezCache) {
		final String fileName = file_name();
		Preconditions.checkArgument(isEzFile(fileName));

		var ezSpec = new EzSpec__(
				fileName,
				file, () -> {
					try {
						return io.readFile(file);
					} catch (FileNotFoundException aE) {
						throw new RuntimeException(aE);
					}
				}
		);

		return super.realParseEzFile(ezSpec, ezCache);
	}

	private String file_name() {
		return this.file.getName();
	}

	@Override
	protected File getFile() {
		return file;
	}

	@Override
	public String getFileName() { return file_name(); }
}
