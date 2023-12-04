package tripleo.elijah.comp.nextgen.impl;

import com.google.common.base.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.io.InputStream;
import tripleo.wrap.File;

public class CK_SourceFile__ElaboratedElijahFile extends __CK_SourceFile__AbstractElijahFile {
	protected final File   directory;
	protected final String file_name;
	protected final File   file;

	public CK_SourceFile__ElaboratedElijahFile(final File aDirectory, final String aFileName) {
		directory = aDirectory;
		file_name = aFileName;
		file      = new File(directory, file_name);
	}

	@Override
	public Operation2<OS_Module> process_query() {
		assert false;
		final ElijahCache           ezCache = null; // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
		//compilation.getCompilationEnclosure().getCompilationRunner().ezCache();
		final Operation2<OS_Module> om      = process_query(compilation.getIO(), ezCache);

		//super.asserverate(); // FIXME 12/03 what is this? eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee

		return om;
	}

	private Operation2<OS_Module> process_query(final IO io, final @NotNull ElijahCache elijahCache) {
		final String fileName = file_name();
		Preconditions.checkArgument(isElijjahFile(fileName));

		final InputStream stream = new Supplier<InputStream>() {
			@Override
			public InputStream get() {
				try {
					return io.readFile(file);
				} catch (FileNotFoundException aE) {
					throw new RuntimeException(aE);
				}
			}
		}.get();

		final ElijahSpec elijahSpec = new ElijahSpec_(fileName, file, stream);

		return __CK_SourceFile__AbstractElijahFile.realParseElijahFile(elijahSpec, elijahCache, compilation);
	}

	private String file_name() {
		return this.file.getName();
	}

	@Override
	protected File getFile() {
		return file;
	}

	@Override
	public String getFileName() {
		return file_name();
	}
}
