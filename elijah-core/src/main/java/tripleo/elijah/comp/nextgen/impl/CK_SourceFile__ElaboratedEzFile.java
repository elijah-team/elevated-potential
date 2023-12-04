package tripleo.elijah.comp.nextgen.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.IO;
import tripleo.elijah.comp.specs.EzCache;
import tripleo.elijah.comp.specs.EzSpec;
import tripleo.elijah.comp.specs.EzSpec__;
import tripleo.elijah.util.*;

import tripleo.wrap.File;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class CK_SourceFile__ElaboratedEzFile extends __CK_SourceFile__AbstractEzFile {
	protected final File   directory;
	protected final String file_name;
	protected final File   file;

	public CK_SourceFile__ElaboratedEzFile(final File aDirectory, final String aFileName) {
		directory = aDirectory;
		file_name = aFileName;
		file      = new File(directory, file_name);
	}

	@Override
	public Operation2<CompilerInstructions> process_query() {
		final EzCache                          ezCache = compilation.getCompilationEnclosure().getCompilationRunner().ezCache();
		final Operation2<CompilerInstructions> oci     = process_query(compilation.getIO(), ezCache);

		super.asserverate();

		return oci;
	}

	private Operation2<CompilerInstructions> process_query(final IO io, final @NotNull EzCache ezCache) {
		final String fileName = file_name();
		Preconditions.checkArgument(isEzFile(fileName));

		var specOp = EzSpec__.of(
				fileName,
				file,
				new Supplier<Operation<InputStream>>() {
					@Override
					public Operation<InputStream> get() {
						try {
							return Operation.success(file.readFile(io));
						} catch (FileNotFoundException aE) {
							return Operation.failure(aE);
						}
					}
				}.get()
								);

		if (specOp.mode() == Mode.SUCCESS) {
			final EzSpec ezSpec = specOp.success();
			return __CK_SourceFile__AbstractEzFile.realParseEzFile(specOp.success(), ezCache);
		} else {
			return Operation2.failure(specOp.failure());
		}
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
