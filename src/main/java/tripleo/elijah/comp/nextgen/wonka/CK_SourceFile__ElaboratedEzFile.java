package tripleo.elijah.comp.nextgen.wonka;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.IO;
import tripleo.elijah.comp.i.CompilationClosure;
import tripleo.elijah.comp.specs.EzCache;
import tripleo.elijah.comp.specs.EzSpec;
import tripleo.elijah.comp.specs.EzSpec__;
import tripleo.elijah.util.*;

import tripleo.elijah_elevated.comp.W;
import tripleo.wrap.File;

import java.util.function.Consumer;

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

		var specOp = EzSpec__.of(fileName, file, W.ezSpec_SIS(file, io));

		if (specOp.mode() == Mode.SUCCESS) {
			final EzSpec ezSpec = specOp.success();
			return __CK_SourceFile__AbstractEzFile.realParseEzFile(specOp.success(), ezCache);
		} else {
			return Operation2.failure(specOp.failure());
		}
	}

	private String file_name() {
		//return this.file.wrappedFileName(); // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
		return this.file.wrapped().toString(); // README 12/07 not just toString, but wrapped.toString
	}

	@Override
	protected File getFile() {
		return file;
	}

	@Override
	public String getFileName() {
		return file_name();
	}

//	@Override
//	public void process_query2(CompilationClosure cc, Consumer<Operation<EzSpec>> cb) {
//		process_query2__(cc.io(), null, cb);
//	}

	@Override
	public void process_query2(final CompilationClosure cc, final Consumer cb) {
		final Operation2<CompilerInstructions> x = process_query(cc.io(), null);
		cb.accept(x);
	}

	public void process_query2__(final IO io, final @NotNull EzCache ezCache, Consumer<Operation<EzSpec>> cb) {
		final String fileName = file_name();
		Preconditions.checkArgument(isEzFile(fileName));

		compilation.modelFactory().mkEzSpec(fileName, file, cb);
	}

}
