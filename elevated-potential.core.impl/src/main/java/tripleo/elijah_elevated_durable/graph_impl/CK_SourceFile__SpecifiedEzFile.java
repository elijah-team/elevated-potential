package tripleo.elijah_elevated_durable.graph_impl;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.IO;
import tripleo.elijah.comp.specs.EzCache;
import tripleo.elijah_fluffy.util.Operation;
import tripleo.elijah_fluffy.util.Operation2;
import tripleo.elijah_elevateder.comp.specs.EDL_EzSpec;
import tripleo.wrap.File;

import java.io.FileNotFoundException;

public class CK_SourceFile__SpecifiedEzFile extends __CK_SourceFile__AbstractEzFile {
	private final File file;

	public CK_SourceFile__SpecifiedEzFile(final File aFile) {
		file = aFile;
	}

	@Override
	public Operation2<CompilerInstructions> process_query() {
		final IO               io       = compilation.getIO();
		final @NotNull EzCache ezCache  = compilation.getCompilationEnclosure().getCompilationRunner().ezCache();
		final String fileName = file_name();
		Preconditions.checkArgument(isEzFile(fileName));

		// FIXME 12/03 use of // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
		var ezSpec = new EDL_EzSpec(
				fileName,
				file, () -> {
					try {
						return Operation.success(file.readFile(io));
					} catch (FileNotFoundException aE) {
						return Operation.failure(aE);
					}
				}
		);

		final Operation2<CompilerInstructions> oci = super.realParseEzFile(ezSpec, ezCache);

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
