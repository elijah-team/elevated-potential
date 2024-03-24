package tripleo.elijah_durable_elevated.elijah.comp.queries;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.graph.i.CK_SourceFile;
import tripleo.elijah.comp.i.CompilationClosure;
import tripleo.elijah.comp.queries.CompilerInstructions_Result;
import tripleo.elijah.comp.queries.QSEZ_Reasoning;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.diagnostic.Locatable;
import tripleo.elijah.util.Operation2;
import tripleo.elijah_durable_elevated.elijah.comp.Compilation;
import tripleo.elijah_durable_elevated.elijah.comp.nextgen.impl.CK_SourceFileFactory;
import tripleo.wrap.File;

import java.io.FilenameFilter;
import java.io.PrintStream;
import java.util.List;
import java.util.regex.Pattern;

public class QuerySearchEzFiles {
	private final          Compilation        c;
	private final @NotNull CompilationClosure cc;
	private final          FilenameFilter     ez_files_filter = new EzFilesFilter();

	public QuerySearchEzFiles(final @NotNull CompilationClosure ccl) {
		c = (Compilation) ccl.getCompilation();

		this.cc = ccl;
	}

	public CompilerInstructions_Result process(final @NotNull tripleo.wrap.File directory) {
		final CompilerInstructions_Result R = new CompilerInstructions_ResultImpl();

		final String[] list = directory.list(ez_files_filter);
		if (list != null) {
			for (final String file_name : list) {
				final CK_SourceFile<CompilerInstructions> sf = CK_SourceFileFactory.get(directory, file_name, CK_SourceFileFactory.K.ElaboratedEzFile);
				sf.associate(cc);
				final Operation2<CompilerInstructions> cio = sf.process_query();

				// reason obv is it is elaborated in the directory ...
				QSEZ_Reasoning reasoning = QSEZ_Reasonings.create(null);
				R.add(cio, reasoning);
			}
		}

		return R;
	}

/*
	public @NotNull List<Operation2<CompilerInstructions>> process2(final @NotNull File directory) {
		final List<Operation2<CompilerInstructions>> R       = new ArrayList<>();
		final ErrSink                                errSink = cc.errSink();

		final String[] list = directory.list(ez_files_filter);
		if (list != null) {
			QSEZ_Reasoning reasoning = QSEZ_Reasonings.create(null);
			List<Operation2<CompilerInstructions>> operation2s = CW_ezDirRequest.apply(
					list,
					directory,
					null,
					(File file1) -> parseEzFile(file1, file1.toString(), cc),
					cc,
					reasoning
			);

			R.addAll(operation2s);
		}

		return R;
	}
*/

	public static class Diagnostic_9995 implements Diagnostic {
		private final File file;
		private final int  code = 9995;

		public Diagnostic_9995(final File aFile) {
			file = aFile;
		}

		@Override
		public @Nullable String code() {
			return "" + code;
		}

		@Override
		public @NotNull Locatable primary() {
			return null;
		}

		@Override
		public void report(final PrintStream stream) {

		}

		@Override
		public @NotNull List<Locatable> secondary() {
			return null;
		}

		@Override
		public @Nullable Severity severity() {
			return null;
		}
	}

	public static class EzFilesFilter implements FilenameFilter {
		@Override
		public boolean accept(final java.io.File file, final String s) {
			final boolean matches2 = Pattern.matches(".+\\.ez$", s);
			return matches2;
		}
	}
}
