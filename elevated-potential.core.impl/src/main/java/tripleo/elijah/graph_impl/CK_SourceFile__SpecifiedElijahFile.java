package tripleo.elijah.graph_impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.IO;
import tripleo.elijah.comp.specs.ElijahCache;
import tripleo.elijah.comp.specs.ElijahSpec;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.util.Operation2;
import tripleo.elijah_elevateder.comp.specs.EDL_ElijahSpec;
import tripleo.wrap.File;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class CK_SourceFile__SpecifiedElijahFile extends __CK_SourceFile__AbstractElijahFile {
	private final File file;

	public CK_SourceFile__SpecifiedElijahFile(final File aFile) {
		file = aFile;
	}

	@Override
	public Operation2<OS_Module> process_query() {
		final ElijahCache           elijahCache = compilation.asCompilation().use_elijahCache();
		final Operation2<OS_Module> oci         = process_query(compilation.getIO(), elijahCache);

		//super.asserverate();

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

		ElijahSpec elijahSpec = new EDL_ElijahSpec(fileName, file, stream);
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
