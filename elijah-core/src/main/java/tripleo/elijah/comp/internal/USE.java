package tripleo.elijah.comp.internal;

import org.apache.commons.lang3.tuple.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.ci_impl.LibraryStatementPartImpl;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.caches.*;
import tripleo.elijah.comp.diagnostic.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.nextgen.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.query.*;
import tripleo.elijah.util.*;

import java.io.*;
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

	public ElijahCache getElijahCache() {
		return elijahCache;
	}

	private final @NotNull ElijahCache elijahCache = new DefaultElijahCache();

	@Contract(pure = true)
	public USE(final @NotNull CompilationClosure cc) {
		c       = cc.getCompilation();
		errSink = cc.errSink();
	}

	public Operation2<OS_Module> findPrelude(final String prelude_name) {
		final CY_FindPrelude cyFindPrelude = new CY_FindPrelude(
				errSink,
				() -> c,
				() -> elijahCache
		);
		return cyFindPrelude.findPrelude(prelude_name);
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
			om = CX_ParseElijahFile.__parseEzFile(
					file_name,
					f,
					c.getIO(),
					c.con().defaultElijahSpecParser2(elijahCache)
			);

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

	public void use(final @NotNull CompilerInstructions compilerInstructions) {
		// TODO

		if (compilerInstructions.getFilename() == null)
			return;

		final File file            = compilerInstructions.makeFile();
		final File instruction_dir = file.getParentFile();

		if (instruction_dir == null) {
			// System.err.println("106106 ************************************** "+file);
			// Prelude.elijjah is a special case
			// instruction_dir = file;
			return;
		}

		for (final LibraryStatementPart lsp : compilerInstructions.getLibraryStatementParts()) {
			final String dir_name = Helpers.remove_single_quotes_from_string(lsp.getDirName());
			final File   dir;// = new File(dir_name);
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

	public enum USE_Reasoning_ {
		USE_Reasoning__parent, USE_Reasoning__child, USE_Reasoning___default, USE_Reasoning__instruction_doer_addon, USE_Reasoning__initial, USE_Reasoning__findStdLib
	}

	public interface USE_Reasoning {
		boolean parent();

		File instruction_dir();

		CompilerInstructions compilerInstructions();

		USE_Reasoning_ ty();
	}

	public static class USE_Reasonings {
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

				@Override
				public USE_Reasoning_ ty() {
					return USE_Reasoning_.USE_Reasoning__parent;
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

				@Override
				public USE_Reasoning_ ty() {
					return USE_Reasoning_.USE_Reasoning__child;
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

				@Override
				public USE_Reasoning_ ty() {
					return USE_Reasoning_.USE_Reasoning___default;
				}
			};
		}

		public static USE_Reasoning instruction_doer_addon(final CompilerInstructions item) {
			return new USE_Reasoning() {
				@Override
				public boolean parent() {
					return false;
				}

				@Override
				public File instruction_dir() {
					return null;
				}

				@Override
				public CompilerInstructions compilerInstructions() {
					return item;
				}

				@Override
				public USE_Reasoning_ ty() {
					return USE_Reasoning_.USE_Reasoning__instruction_doer_addon;
				}
			};
		}

		public static USE_Reasoning findStdLib(final CD_FindStdLib aFindStdLib) {
			return new USE_Reasoning() {
				@Override
				public boolean parent() {
					return false;
				}

				@Override
				public File instruction_dir() {
					return null;
				}

				@Override
				public CompilerInstructions compilerInstructions() {
					return aFindStdLib.maybeFoundResult();
				}

				@Override
				public USE_Reasoning_ ty() {
					return USE_Reasoning_.USE_Reasoning__findStdLib;
				}
			};
		}

		public static USE_Reasoning initial(final Triple<CR_ProcessInitialAction, CompilationRunner, CB_Output> triple) {
			return new USE_Reasoning() {
				@Override
				public boolean parent() {
					return false;
				}

				@Override
				public File instruction_dir() {
					return null;
				}

				@Override
				public CompilerInstructions compilerInstructions() {
					var left = triple.getLeft();

					return left.maybeFoundResult();
				}

				@Override
				public USE_Reasoning_ ty() {
					return USE_Reasoning_.USE_Reasoning__initial;
				}
			};
		}
	}
}
