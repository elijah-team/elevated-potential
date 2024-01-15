package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.ci_impl.LibraryStatementPartImpl;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.caches.DefaultElijahCache;
import tripleo.elijah.comp.generated.CompilationAlways;
import tripleo.elijah.comp.graph.i.CM_Module;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.local.CW;
import tripleo.elijah.comp.local.CW_sourceDirRequest;
import tripleo.elijah.comp.local.CY_FindPrelude;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.diagnostic.ExceptionDiagnostic;
import tripleo.elijah.diagnostic.FileNotFoundDiagnostic;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.util.*;

import java.io.*;
import tripleo.wrap.File;

import java.util.function.Function;
import java.util.regex.*;

@SuppressWarnings("UnnecessaryLocalVariable")
public class USE {
	private static final   FilenameFilter accept_source_files = (directory, file_name) -> {
		final boolean matches = Pattern.matches(".+\\.elijah$", file_name)
				|| Pattern.matches(".+\\.elijjah$", file_name);
		return matches;
	};
	private final @NotNull Compilation   c;
	private final @NotNull ErrSink        errSink;

	public ElijahCache getElijahCache() {
		return elijahCache;
	}

	private final @NotNull ElijahCache elijahCache = new DefaultElijahCache();

	@Contract(pure = true)
	public USE(final @NotNull CompilationClosure cc) {
		c       = (@NotNull Compilation) cc.getCompilation();
		errSink = cc.errSink();
	}

	public Operation2<OS_Module> findPrelude(final String prelude_name) {
		final CY_FindPrelude cyFindPrelude = new CY_FindPrelude(
				() -> c,
				() -> elijahCache
		);
		return cyFindPrelude.findPrelude(prelude_name);
	}

	private Operation2<OS_Module> parseElijjahFile(final @NotNull File f,
	                                               final @NotNull String file_name,
	                                               final @NotNull LibraryStatementPart lsp) {
		logProgress(CompProgress.USE__parseElijjahFile, f.getAbsolutePath());

		if (!f.exists()) {
			final Diagnostic e = new FileNotFoundDiagnostic(f.wrapped()); // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee

			return Operation2.failure(e);
		}

		Operation2<OS_Module> om;

		try {
			final CW.CX_ParseElijahFile.ElijahSpecReader specReader = new CW.CX_ParseElijahFile.ElijahSpecReader() {
				@Override
				public @NotNull Operation<InputStream> get() {
					return ((CompilationImpl)c).defaultElijahSpecParser(f);
				}
			};

			final CY_ElijahSpecParser specParser = c.con().defaultElijahSpecParser(elijahCache);
			om = CW.CX_ParseElijahFile.__parseEzFile(file_name, f, specReader, specParser);

			switch (om.mode()) {
			case SUCCESS -> {
				final OS_Module mm = om.success();
				final CM_Module cm = c.megaGrande(mm);

				//assert mm.getLsp() == null;
				//assert mm.prelude() == null;

				cm.advise(lsp);
				cm.advise(() -> findPrelude(CompilationAlways.defaultPrelude()));

				return om;
			}
			default -> {
				return om;
			}
			}
		} catch (final Exception aE) {
			return Operation2.failure(new ExceptionDiagnostic(aE));
		}
	}

	private void logProgress(final CompProgress aCompProgress, final String aAbsolutePath) {
		this.c.getCompilationEnclosure().logProgress(aCompProgress,aAbsolutePath);
	}

	public void use(final @NotNull CompilerInstructions compilerInstructions) {
		// TODO

		if (compilerInstructions.getFilename() == null)
			return;

		final File file            = compilerInstructions.makeFile();
		final File instruction_dir = file.getParentFile();

		if (instruction_dir == null) {
			 SimplePrintLoggerToRemoveSoon.println_err_4("106106 ************************************** "+file);
			// Prelude.elijjah is a special case
			// instruction_dir = file;
			return;
		}

		for (final LibraryStatementPart lsp : compilerInstructions.getLibraryStatementParts()) {
			final String dir_name = Helpers.remove_single_quotes_from_string(lsp.getDirName());
			final File    dir;// = new File(dir_name);
			USE_Reasoning reasoning = null;
			if (dir_name.equals("..")) {
				dir = instruction_dir/* .getAbsoluteFile() */.getParentFile(); // FIXME 09/26 this has always been questionable
				reasoning = USE_Reasonings.parent(compilerInstructions, true, instruction_dir, lsp);
			} else {
				dir = new File(instruction_dir, dir_name);
				reasoning = USE_Reasonings.child(compilerInstructions, false, instruction_dir, dir_name, dir, lsp);
			}
			use_internal(dir, lsp, reasoning);
		}

		final LibraryStatementPart lsp = new LibraryStatementPartImpl();
		lsp.setName(Helpers0.makeToken("default")); // TODO: make sure this doesn't conflict
		lsp.setDirName(Helpers0.makeToken(String.format("\"%s\"", instruction_dir)));
		lsp.setInstructions(compilerInstructions);
		USE_Reasoning reasoning = USE_Reasonings.default_(compilerInstructions, false, instruction_dir, lsp);
		use_internal(instruction_dir, lsp, reasoning);
	}

	private void use_internal(final @NotNull File dir, final LibraryStatementPart lsp, USE_Reasoning aReasoning) {
		if (dir.wrapped() == null) {
			int y=2;
		}
		if (!dir.isDirectory()) {
			errSink.reportError("9997 Not a directory " + dir);
			return;
		}

		final @NotNull File[] files = dir.listFiles(accept_source_files);
		if (files != null) {
			final USE _u = this;
			final Function<File, Operation2<OS_Module>> ffom = new Function<>() {
				@Override
				public Operation2<OS_Module> apply(final File file) {
					return CW._use(file, _u, lsp);
				}
			};
			CW_sourceDirRequest.apply(files, dir, lsp, ffom, c, aReasoning);
		}
	}

	public Operation2<OS_Module> __parseElijahFile(final File aFile, final String aFileName, final LibraryStatementPart aLsp) {
		return parseElijjahFile(aFile, aFileName, aLsp);
	}
}
