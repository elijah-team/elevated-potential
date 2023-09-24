package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.caches.*;
import tripleo.elijah.comp.diagnostic.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.query.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;


@SuppressWarnings("UnnecessaryLocalVariable")
public class USE {
	private final @NotNull Compilation c;
	private final @NotNull ErrSink errSink;
	private final @NotNull ElijahCache elijahCache = new DefaultElijahCache();

	@Contract(pure = true)
	public USE(final @NotNull Compilation aCompilation) {
		c       = aCompilation;
		errSink = c.getErrSink();
	}

	public Operation2<OS_Module> findPrelude(final String prelude_name) {
		return new CY_FindPrelude(errSink, this).findPrelude(prelude_name);
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

		return calm;
	}

	public Operation2<OS_Module> realParseElijjahFile(final String f, final @NotNull File file, final boolean do_out) throws Exception {
		try (final InputStream s = c.getIO().readFile(file)) {
			final ElijahSpec spec = new ElijahSpec(f, file, s, do_out);
			return Operation2.convert(realParseElijjahFile(spec));
		}
	}

	public void use(final @NotNull CompilerInstructions compilerInstructions, final boolean do_out) throws Exception {
		// TODO

		if (compilerInstructions.getFilename() == null) return;


		final File instruction_dir = new File(compilerInstructions.getFilename()).getParentFile();
		for (final LibraryStatementPart lsp : compilerInstructions.getLibraryStatementParts()) {
			final String dir_name = Helpers.remove_single_quotes_from_string(lsp.getDirName());
			final File   dir;// = new File(dir_name);
			if (dir_name.equals(".."))
				dir = instruction_dir/*.getAbsoluteFile()*/.getParentFile();
			else
				dir = new File(instruction_dir, dir_name);
			use_internal(dir, do_out, lsp);
		}
		final LibraryStatementPart lsp = new LibraryStatementPartImpl();
		lsp.setName(Helpers.makeToken("default")); // TODO: make sure this doesn't conflict
		lsp.setDirName(Helpers.makeToken(String.format("\"%s\"", instruction_dir)));
		lsp.setInstructions(compilerInstructions);
		use_internal(instruction_dir, do_out, lsp);
	}

	private void use_internal(final @NotNull File dir, final boolean do_out, final LibraryStatementPart lsp) {
		if (!dir.isDirectory()) {
			errSink.reportError("9997 Not a directory " + dir);
			return;
		}
		//
		final File[] files = dir.listFiles(accept_source_files);
		if (files != null) {
			for (final File file : files) {
//				final CompFactory.InputRequest inp = c.con().createInputRequest(file, do_out, lsp);

				final String                file_name = file.toString();
				final Operation2<OS_Module> om        = parseElijjahFile(file, file_name, do_out, lsp);

				if (om.mode() == Mode.FAILURE) {
					System.err.println("204 " + om.failure());

					var d = om.failure().get();
					if (d instanceof Exception e) {
						// help!!
						e.printStackTrace();
					}
					System.err.println(d.getClass().getName());
				}

//				c.reports().addInput(inp, Finally.Out2.ELIJAH);
				c.reports().addInput(() -> file_name, Finally.Out2.ELIJAH);
			}
		}
	}

	private Operation2<OS_Module> parseElijjahFile(final @NotNull File f,
												   final @NotNull String file_name,
												   final boolean do_out,
												   final @NotNull LibraryStatementPart lsp) {
		System.out.printf("   %s%n", f.getAbsolutePath());

		if (!f.exists()) {
			final Diagnostic e = new FileNotFoundDiagnostic(f);

			return Operation2.failure(e);
		}

		Operation2<OS_Module> om;

		try {
			om = realParseElijjahFile(file_name, f, do_out);

			switch (om.mode()) {
				case SUCCESS -> {
					final OS_Module mm = om.success();

					//assert mm.getLsp() == null;
					//assert mm.prelude == null;

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

	private static final FilenameFilter accept_source_files = (directory, file_name) -> {
		final boolean matches = Pattern.matches(".+\\.elijah$", file_name)
				|| Pattern.matches(".+\\.elijjah$", file_name);
		return matches;
	};
}

//public class USE {
//	private static final FilenameFilter         accept_source_files = new FilenameFilter() {
//		@Override
//		public boolean accept(final File directory, final String file_name) {
//			final boolean matches = Pattern.matches(".+\\.elijah$", file_name)
//					|| Pattern.matches(".+\\.elijjah$", file_name);
//			return matches;
//		}
//	};
//	private final        Compilation            c;
//	private final        Map<String, WorldModule> fn2m = new HashMap<String, WorldModule>();
//
//	private final Map<String, Operation2<WorldModule>> preludeMap = new HashMap<>();
//
//	@Contract(pure = true)
//	public USE(final Compilation aCompilation) {
//		c       = aCompilation;
//
//		c.world().addModuleProcess(new CompletableProcess<WorldModule>() {
//			@Override
//			public void add(final WorldModule module) {
//				final String fn = module.module().getFileName();
//				fn2m.put(fn, module);
//			}
//
//			@Override
//			public void complete() {
//
//			}
//
//			@Override
//			public void error(final Diagnostic d) {
//
//			}
//
//			@Override
//			public void preComplete() {
//
//			}
//
//			@Override
//			public void start() {
//
//			}
//		});
//	}
//
//	private Operation2<WorldModule> parseElijjahFile(final @NotNull InputRequest aInputRequest) {
//		Operation2<WorldModule> owm;
//		final File f = aInputRequest.file();
//		final LibraryStatementPart lsp = aInputRequest.lsp();
//
//		c.reports().addInput(() -> {
//			String s = aInputRequest.file().toString();
//			System.err.println("6689 ADD INPUT "+s);
//			return s;
//        }, Finally.Out2.ELIJAH);
//		c.getCompilationEnclosure().logProgress(CompProgress.__parseElijjahFile_InputRequest, aInputRequest);
//
//		if (f.exists()) {
//			final Operation2<WorldModule> om = realParseElijjahFile2(aInputRequest);
//
//			if (om.mode() == Mode.SUCCESS) {
//				// TODO we don't know which prelude to find yet
//				final Operation2<WorldModule> pl = findPrelude(Compilation.CompilationAlways.defaultPrelude());
//
//				// NOTE Go: infectious, tedious; also slightly lazy
//				assert pl.mode() == Mode.SUCCESS;
//
//				final WorldModule mm1 = om.success();
//				final OS_Module   mm  = mm1.module();
//
//				if (mm.getLsp() == null) {
//					//assert mm.prelude  == null;
//					mm.setLsp(lsp);
//					mm.setPrelude(pl.success().module()); // FIXME 08/31
//				}
//
//				owm = Operation2.success(mm1);
//			} else if (om.failure() instanceof ExceptionDiagnostic) {
//				final Diagnostic e = om.failure();
//				owm = Operation2.failure(e);
//			} else {
//				final Diagnostic e = new UnknownExceptionDiagnostic(om);
//				owm = Operation2.failure(e);
//			}
//		} else {
//			final Diagnostic e = new FileNotFoundDiagnostic(f);
//
//			owm = Operation2.failure(e);
//		}
//		aInputRequest.setOp(owm);
//		return owm;
//	}
//
//	public Operation2<WorldModule> realParseElijjahFile2(final @NotNull InputRequest aInputRequest) {
//		final Operation<WorldModule> om;
//
//		try {
//			om = realParseElijjahFile(aInputRequest);
//
//			aInputRequest.setOp(Operation2.convert(om));
//			assert aInputRequest.op() != null;
//			assert aInputRequest.op().mode() == Mode.SUCCESS;
//		} catch (Exception aE) {
//			aE.printStackTrace();
//			return Operation2.failure(new ExceptionDiagnostic(aE));
//		}
//
//		switch (om.mode()) {
//		case SUCCESS:
//			return Operation2.convert(om);
//		case FAILURE:
//			final Exception e = om.failure();
//			c.getErrSink().exception(e);
//			return Operation2.failure(new ExceptionDiagnostic(e));
//		default:
//			throw new IllegalStateException("Unexpected value: " + om.mode());
//		}
//	}
//
//	public Operation<WorldModule> realParseElijjahFile(final @NotNull InputRequest aInputRequest) {
//		var file   = aInputRequest.file();
//		var f      = aInputRequest.file().toString();
//		var do_out = aInputRequest.do_out();
//
//		try {
//			final String absolutePath = file.getCanonicalFile().toString();
//			if (fn2m.containsKey(absolutePath)) { // don't parse twice
//				final WorldModule m = fn2m.get(absolutePath);
//				return Operation.success(m);
//			}
//
//			final IO io = c.getIO();
//
//			// tree add something
//
//			final InputStream          s  = io.readFile(file);
//			final Operation<OS_Module> om = parseFile_(f, s, do_out);
//			if (om.mode() != Mode.SUCCESS) {
//				final Exception e = om.failure();
//				assert e != null;
//
//				System.err.println("parser exception: " + e);
//				e.printStackTrace(System.err);
//				s.close();
//				return Operation.failure(e);
//			}
//
//			final WorldModule R = new DefaultWorldModule(om.success(), c.getCompilationEnclosure());
//			c.world().addModule2(R);
//
//			s.close();
//			return Operation.success(R);
//		} catch (IOException aE) {
//			return Operation.failure(aE);
//		}
//	}
//
//	private @NotNull Operation<OS_Module> parseFile_(final String f, final InputStream s, final boolean do_out) {
//		final QuerySourceFileToModuleParams qp = new QuerySourceFileToModuleParams(do_out, s, f);
//		final QuerySourceFileToModule       q  = new QuerySourceFileToModule(qp, c);
//		return q.calculate();
//	}
//
//	public Operation2<WorldModule> findPrelude(final String prelude_name) {
//		if (preludeMap.containsKey(prelude_name)) {
//			return preludeMap.get(prelude_name);
//		}
//
//		var x = findPrelude2(prelude_name);
//		preludeMap.put(prelude_name, x);
//		return x;
//	}
//
//	private @NotNull Operation2<WorldModule> findPrelude2(final String prelude_name) {
//		final File local_prelude = new File("lib_elijjah/lib-" + prelude_name + "/Prelude.elijjah");
//
//		if (!(local_prelude.exists())) {
//			return Operation2.failure(new FileNotFoundDiagnostic(local_prelude));
//		}
//
//		final Operation2<WorldModule> om = realParseElijjahFile2(c.con().createInputRequest(local_prelude, c.cfg().do_out, null)); // TODO 09/05 fix null
//		if (om.mode() == FAILURE) {
//			om.failure().report(System.out);
//			return om;
//		}
//		assert om.mode() == Mode.SUCCESS;
//		return om;
//	}
//
//	public void use(final @NotNull CompilerInstructions compilerInstructions, final boolean do_out) {
//		final File instruction_dir = new File(compilerInstructions.getFilename()).getParentFile();
//		for (final LibraryStatementPart lsp : compilerInstructions.lsps()) {
//			final String dir_name = Helpers.remove_single_quotes_from_string(lsp.getDirName());
//			File         dir;
//			if (dir_name.equals(".."))
//				dir = instruction_dir/*.getAbsoluteFile()*/.getParentFile();
//			else
//				dir = new File(instruction_dir, dir_name);
//			use_internal(dir, do_out, lsp);
//		}
//		final LibraryStatementPart lsp = new LibraryStatementPartImpl();
//		lsp.setName(Helpers.makeToken("default")); // TODO: make sure this doesn't conflict
//		lsp.setDirName(Helpers.makeToken(String.format("\"%s\"", instruction_dir)));
//		lsp.setInstructions(compilerInstructions);
//		use_internal(instruction_dir, do_out, lsp);
//	}
//
//	private void use_internal(final @NotNull File dir, final boolean do_out, @NotNull LibraryStatementPart lsp) {
//		if (!dir.isDirectory()) {
//			c.getErrSink().reportError("9997 Not a directory " + dir);
//			return;
//		}
//		//
//		final File[] files = dir.listFiles(accept_source_files);
//		if (files != null) {
//			for (final File file : files) {
//				parseElijjahFile(c.con().createInputRequest(file, do_out, lsp));
//			}
//		}
//	}
//}
