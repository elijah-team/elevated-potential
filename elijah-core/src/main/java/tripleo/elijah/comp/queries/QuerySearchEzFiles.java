package tripleo.elijah.comp.queries;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.diagnostic.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.impl.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class QuerySearchEzFiles {
	private final Compilation c;
	private final @NotNull CompilationClosure cc;
	private final FilenameFilter ez_files_filter = new EzFilesFilter();

	public QuerySearchEzFiles(final @NotNull CompilationClosure ccl) {
		c = ccl.getCompilation();

		this.cc = ccl;
	}

	@Nullable
	CompilerInstructions parseEzFile(final @NotNull File f, final @NotNull String file_name,
	                                 final @NotNull CompilationClosure cc) {
		var p = new SourceFileParserParams(null, f, file_name, cc);
		return c.getCompilationEnclosure().getCompilationRunner().parseEzFile(p).success();
	}

	public @NotNull Operation2<List<CompilerInstructions>> process(final @NotNull File directory) {
		final List<CompilerInstructions> R       = new ArrayList<>();
		final ErrSink                    errSink = cc.errSink();

		final String[] list = directory.list(ez_files_filter);
		if (list != null) {
			for (final String file_name : list) {
				try {
					final File                 file   = new File(directory, file_name);
					final CompilerInstructions ezFile = parseEzFile(file, file.toString(), cc);
					if (ezFile != null)
						R.add(ezFile);
					else
						errSink.reportError("9995 ezFile is null " + file); // TODO Diagnostic
				} catch (final Exception e) {
					return Operation2.failure(new ExceptionDiagnostic(e));
				}
			}
		}

		return Operation2.success(R);
	}

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
