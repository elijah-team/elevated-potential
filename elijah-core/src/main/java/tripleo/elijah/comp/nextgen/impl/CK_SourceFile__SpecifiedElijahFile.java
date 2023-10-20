package tripleo.elijah.comp.nextgen.impl;

import com.google.common.base.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.*;

import java.io.*;

public class CK_SourceFile__SpecifiedElijahFile extends __CK_SourceFile__AbstractElijahFile {
	private final File file;

	public CK_SourceFile__SpecifiedElijahFile(final File aFile) {
		file = aFile;
	}

	@Override
	public Operation2<OS_Module> process_query() {
		final ElijahCache              elijahCache = compilation.use_elijahCache();
		final Operation2<OS_Module> oci     = process_query(compilation.getIO(), elijahCache);

		super.asserverate();

		return oci;
	}

	private Operation2<OS_Module> process_query(final IO io, final @NotNull ElijahCache aElijahCache) {
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

		ElijahSpec elijahSpec = new ElijahSpec_(fileName, file, stream);
		return realParseElijahFile(elijahSpec, aElijahCache);
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
