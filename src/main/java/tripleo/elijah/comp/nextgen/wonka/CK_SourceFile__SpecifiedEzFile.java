package tripleo.elijah.comp.nextgen.wonka;

import com.google.common.base.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.CompilationClosure;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.util.function.Consumer;

import tripleo.wrap.File;

public class CK_SourceFile__SpecifiedEzFile extends __CK_SourceFile__AbstractEzFile {
	private final File file;

	public CK_SourceFile__SpecifiedEzFile(final File aFile) {
		file = aFile;
	}

	@Override
	public Operation2<CompilerInstructions> process_query() {
		final IO                               io      = compilation.getIO();
		final EzCache                          ezCache = compilation.getCompilationEnclosure().getCompilationRunner().ezCache();
		final Operation2<CompilerInstructions> oci     = process_query(io, ezCache);

		super.asserverate();

		return oci;
	}

	@Override
	public void process_query2(final CompilationClosure cc, final Consumer cb) {
		process_query(cc.io(), null);
	}

	private Operation2<CompilerInstructions> process_query(final IO io, final EzCache ezCache) {
		if (ezCache == null) {
			System.err.println("ezCache is null");
			return Operation2.failure("ezCache is null");
		}
		final String fileName = file_name();
		Preconditions.checkArgument(isEzFile(fileName));

		// FIXME 12/03 use of // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
		var ezSpec = new EzSpec__(
				fileName,
				file, () -> {
					try {
						return file.readFile(io);
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
