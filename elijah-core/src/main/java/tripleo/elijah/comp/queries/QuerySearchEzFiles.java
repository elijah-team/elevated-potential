package tripleo.elijah.comp.queries;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.diagnostic.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.impl.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.nextgen.impl.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class QuerySearchEzFiles {
	private final          Compilation        c;
	private final @NotNull CompilationClosure cc;
	private final          FilenameFilter     ez_files_filter = new EzFilesFilter();

	public QuerySearchEzFiles(final @NotNull CompilationClosure ccl) {
		c = ccl.getCompilation();

		this.cc = ccl;
	}

	public @NotNull List<Operation2<CompilerInstructions>> process(final @NotNull File directory) {
		final List<Operation2<CompilerInstructions>> R       = new ArrayList<>();

		final String[] list = directory.list(ez_files_filter);
		if (list != null) {
			for (final String file_name : list) {
				final CK_SourceFile                   sf  = CK_SourceFileFactory.get(directory, file_name, CK_SourceFileFactory.K.ElaboratedEzFile);
				final Operation<CompilerInstructions> cio = sf.process_query();

				// reason obv is it is elaborated in the directory ...
//				QSEZ_Reasoning reasoning = QSEZ_Reasonings.create(null);
				R.add(Operation2.convert(cio));
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
		public boolean accept(final File file, final String s) {
			final boolean matches2 = Pattern.matches(".+\\.ez$", s);
			return matches2;
		}
	}
}
