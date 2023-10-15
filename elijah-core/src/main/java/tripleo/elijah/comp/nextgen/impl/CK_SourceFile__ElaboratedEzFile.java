package tripleo.elijah.comp.nextgen.impl;

import com.google.common.base.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.util.*;

import java.io.*;

public class CK_SourceFile__ElaboratedEzFile extends __CK_SourceFile__AbstractEzFile {
	protected final File          directory;
	protected final String        file_name;
	protected final File          file;

	public CK_SourceFile__ElaboratedEzFile(final File aDirectory, final String aFileName) {
		directory = aDirectory;
		file_name = aFileName;
		file      = new File(directory, file_name);
	}

	private Operation2<CompilerInstructions> process_query(final IO io, final @NotNull EzCache ezCache) {
		final String fileName = file_name();
		Preconditions.checkArgument(isEzFile(fileName));

		var ezSpec = new EzSpec(
				fileName,
				file, () -> {
					try {
						return io.readFile(file);
					} catch (FileNotFoundException aE) {
						throw new RuntimeException(aE);
					}
				}
		);

		return __CK_SourceFile__AbstractEzFile.realParseEzFile(ezSpec, ezCache);
	}

	@Override
	public Operation2<CompilerInstructions> process_query() {
		final EzCache                          ezCache = compilation.getCompilationEnclosure().getCompilationRunner().ezCache();
		final Operation2<CompilerInstructions> oci     = process_query(compilation.getIO(), ezCache);

		super.asserverate();

		return oci;
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
