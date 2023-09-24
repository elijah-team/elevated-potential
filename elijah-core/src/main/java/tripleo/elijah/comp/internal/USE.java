package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.ci_impl.LibraryStatementPartImpl;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.caches.*;
import tripleo.elijah.comp.diagnostic.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.query.*;
import tripleo.elijah.util.*;
import tripleo.elijah.world.i.*;
import tripleo.elijah.world.impl.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;

@SuppressWarnings("UnnecessaryLocalVariable")
public class USE {
	private static final   FilenameFilter accept_source_files = (directory, file_name) -> {
		final boolean matches = Pattern.matches(".+\\.elijah$", file_name)
				|| Pattern.matches(".+\\.elijjah$", file_name);
		return matches;
	};
	private final @NotNull Compilation    c;
	private final @NotNull ErrSink        errSink;

	private final @NotNull ElijahCache elijahCache = new DefaultElijahCache();

	@Contract(pure = true)
	public USE(final @NotNull CompilationClosure cc) {
		c       = cc.getCompilation();
		errSink = cc.errSink();
	}

	public Operation2<OS_Module> findPrelude(final String prelude_name) {
		return new CY_FindPrelude(errSink, this).findPrelude(prelude_name);
	}

	private Operation2<OS_Module> parseElijjahFile(final @NotNull File f,
	                                               final @NotNull String file_name,
	                                               final @NotNull LibraryStatementPart lsp) {
		this.c.getCompilationEnclosure().logProgress(CompProgress.USE__parseElijjahFile, f.getAbsolutePath());

		if (!f.exists()) {
			final Diagnostic e = new FileNotFoundDiagnostic(f);

			return Operation2.failure(e);
		}

		Operation2<OS_Module> om;

		try {
			om = realParseElijjahFile(file_name, f);

			switch (om.mode()) {
				case SUCCESS -> {
					final OS_Module mm = om.success();

					// assert mm.getLsp() == null;
					// assert mm.prelude == null;

					if (mm.getLsp() == null) {
						// TODO we don't know which prelude to find yet
						final Operation2<OS_Module> pl = findPrelude(Compilation.CompilationAlways.defaultPrelude());

						// NOTE Go. infectious. tedious. also slightly lazy
						assert pl.mode() == Mode.SUCCESS;

						mm.setLsp(lsp);
						mm.setPrelude(pl.success());
					}
					return Operation2.success(mm);
				}
				default -> {
					return om;
				}
			}
		} catch (final Exception aE) {
			return Operation2.failure(new ExceptionDiagnostic(aE));
		}
	}

	public Operation<OS_Module> realParseElijjahFile(final ElijahSpec spec) {
		final File file = spec.file();

		final String absolutePath;
		try {
			absolutePath = file.getCanonicalFile().toString();
		} catch (final IOException aE) {
			return Operation.failure(aE);
		}

		final Optional<OS_Module> early = elijahCache.get(absolutePath);

		if (early.isPresent()) {
			return Operation.success(early.get());
		}

		final var calm = CX_ParseElijahFile.parseAndCache(spec, elijahCache, absolutePath, c);

		final WorldModule worldModule = new DefaultWorldModule(calm.success(), c.getCompilationEnclosure());
		c.world().addModule2(worldModule);

		return calm;
	}

	public Operation2<OS_Module> realParseElijjahFile(final @NotNull String file_name, final @NotNull File file)
			throws Exception {
		return CX_ParseElijahFile.__parseEzFile(file_name,
		                                        file,
		                                        c,
		                                        (spec) -> Operation2.convert(realParseElijjahFile(spec))
		);
	}

	public void use(final @NotNull CompilerInstructions compilerInstructions) throws Exception {
		// TODO

		if (compilerInstructions.getFilename() == null)
			return;

		final File instruction_dir = new File(compilerInstructions.getFilename()).getParentFile();
		for (final LibraryStatementPart lsp : compilerInstructions.getLibraryStatementParts()) {
			final String dir_name = Helpers.remove_single_quotes_from_string(lsp.getDirName());
			final File   dir;// = new File(dir_name);
			USE_Reasoning reasoning = null;
			if (dir_name.equals("..")) {
				dir = instruction_dir/* .getAbsoluteFile() */.getParentFile();
				reasoning = USE_Reasonings.parent(compilerInstructions, true, instruction_dir, lsp);
			} else {
				dir = new File(instruction_dir, dir_name);
				reasoning = USE_Reasonings.child(compilerInstructions, false, instruction_dir, dir_name, dir, lsp);
			}
			use_internal(dir, lsp, reasoning);
		}

		final LibraryStatementPart lsp = new LibraryStatementPartImpl();
		lsp.setName(Helpers.makeToken("default")); // TODO: make sure this doesn't conflict
		lsp.setDirName(Helpers.makeToken(String.format("\"%s\"", instruction_dir)));
		lsp.setInstructions(compilerInstructions);
		USE_Reasoning reasoning = USE_Reasonings.default_(compilerInstructions, false, instruction_dir, lsp);
		use_internal(instruction_dir, lsp, reasoning);
	}

	private void use_internal(final @NotNull File dir, final LibraryStatementPart lsp, USE_Reasoning aReasoning) {
		if (!dir.isDirectory()) {
			errSink.reportError("9997 Not a directory " + dir);
			return;
		}
		//
		final File[] files = dir.listFiles(accept_source_files);
		if (files != null) {
			CW_sourceDirRequest.apply(files, dir, lsp, (File file) -> {
				final String file_name = file.toString();
				return parseElijjahFile(file, file_name, lsp);
			}, c, aReasoning);
		}
	}

	public Compilation _c() {
		return c;
	}

	public interface USE_Reasoning {
		boolean parent();

		File instruction_dir();

		CompilerInstructions compilerInstructions();
	}

	static class USE_Reasonings {
		public static USE_Reasoning parent(CompilerInstructions aCompilerInstructions, boolean parent, File aInstructionDir, LibraryStatementPart aLsp) {
			return new USE_Reasoning() {
				@Override
				public boolean parent() {
					return parent;
				}

				@Override
				public File instruction_dir() {
					return aInstructionDir;
				}

				@Override
				public CompilerInstructions compilerInstructions() {
					return aCompilerInstructions;
				}
			};
		}

		public static USE_Reasoning child(CompilerInstructions aCompilerInstructions, boolean parent, File aInstructionDir, String aDirName, File aDir, LibraryStatementPart aLsp) {
			return new USE_Reasoning() {
				File top() {
					return new File(aDirName);
				}

				File child() {
					return aInstructionDir;
				}

				@Override
				public boolean parent() {
					return parent;
				}

				@Override
				public File instruction_dir() {
					return aDir;
				}

				@Override
				public CompilerInstructions compilerInstructions() {
					return aCompilerInstructions;
				}
			};
		}

		public static USE_Reasoning default_(CompilerInstructions aCompilerInstructions, boolean parent, File aInstructionDir, LibraryStatementPart aLsp) {
			return new USE_Reasoning() {
				@Override
				public boolean parent() {
					return false;
				}

				@Override
				public File instruction_dir() {
					return aInstructionDir;
				}

				@Override
				public CompilerInstructions compilerInstructions() {
					return aCompilerInstructions;
				}
			};
		}
	}
}
