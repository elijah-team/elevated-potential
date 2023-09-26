package tripleo.elijah.comp.internal;

import org.apache.commons.lang3.tuple.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.queries.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.util.*;

import java.io.*;

import static tripleo.elijah.nextgen.query.Mode.*;

class EzM {
	private final CompilationEnclosure ce;
	private final QueryEzFileToModule  query;
	private /*final*/ EzCache              ezCache;

	EzM(CompilationEnclosure aCe) {
		ce    = aCe;
		query = new QueryEzFileToModule();

		ce.waitCompilationRunner(cr1 -> {
			assert cr1 != null;
			ezCache = cr1.ezCache();
		} );
	}

	private void logProgress(final IProgressSink.Codes code, final String message) {
		ce.logProgress(CompProgress.EzM__logProgress, Pair.of(code, message));
	}

	@NotNull
	Operation<CompilerInstructions> parseEzFile1(final @NotNull SourceFileParserParams p) {
		@NotNull final File f = p.f();

		logProgress(IProgressSink.Codes.EzM__parseEzFile1, f.getAbsolutePath());

		if (!f.exists()) {
			p.cc().errSink().reportError("File doesn't exist " + f.getAbsolutePath());

			return Operation.failure(new FileNotFoundException());
		} else {
			final Operation<CompilerInstructions> om = realParseEzFile(p);
			return om;
		}
	}

	@NotNull
	Operation<CompilerInstructions> realParseEzFile(final @NotNull SourceFileParserParams p) {
		try {
			final EzSpec                          ezSpec             = p.getEzSpec();
			final String                          f                  = ezSpec.f();
			final File                            file               = ezSpec.file();
			final InputStream                     s                  = ezSpec.s();
			final CompilationClosure              compilationClosure = p.cc();
			final Operation<CompilerInstructions> oci                = realParseEzFile(f, s, file, compilationClosure.getCompilation());

			if (/* false || */ oci.mode() == SUCCESS) {
				Operation<String> hash = new CA_getHashForFile().apply(p.file_name(), p.f());
				logProgress(IProgressSink.Codes.EzM__realParseEzFile, hash.success());

				var c = compilationClosure.getCompilation();
				c.getObjectTree().asseverate(Triple.of(ezSpec, p, hash), Asseverate.CI_HASHED);
			}

			return oci;
		} catch (FileNotFoundException aE) {
			return Operation.failure(aE);
		}
	}

	@NotNull
	Operation<CompilerInstructions> realParseEzFile(final String f,
	                                                final @Nullable InputStream s,
	                                                final @NotNull File file,
	                                                final @NotNull Compilation c) {
		final String absolutePath;
		try {
			absolutePath = file.getCanonicalFile().toString(); // TODO 04/10 hash this and "attach"
			// queryDB.attach(compilerInput, new EzFileIdentity_Sha256($hash)); // ??
		} catch (IOException aE) {
			return Operation.failure(aE);
		}

		var opt_ci = ezCache.get(absolutePath);
		if (opt_ci.isPresent()) {
			CompilerInstructions compilerInstructions = opt_ci.get();

			// TODO 04/10
			// /*EzFileIdentity??*/(MAP/*??*/, resolver is try stmt)
			// ...queryDB.attach(compilerInput, new EzFileIdentity_Sha256($hash)); // ??
			c.getObjectTree().asseverate(compilerInstructions, Asseverate.CI_CACHED);

			return Operation.success(compilerInstructions);
		}

		try {
			EzSpec                                ezSpec = new EzSpec(f, s, file);
			final Operation<CompilerInstructions> cio    = parseEzFile_(ezSpec, c);

			switch (cio.mode()) {
				case FAILURE -> {
					final Exception e = cio.failure();
					assert e != null;
					Stupidity.println_err_2(("parser exception: " + e));
					e.printStackTrace(System.err);
					return cio;
				}
				case SUCCESS -> {
					final CompilerInstructions R = cio.success();
					R.setFilename(file.toString());
					ezCache.put(ezSpec, absolutePath, R);
					c.getObjectTree().asseverate(Triple.of(ezSpec, cio, R), Asseverate.CI_SPECCED);
					c.getObjectTree().asseverate(R, Asseverate.CI_PARSED);
					return cio;
				}
				default -> throw new IllegalStateException("Unexpected value: " + cio.mode());
			}
		} finally {
			if (s != null) {
				try {
					s.close();
				} catch (IOException aE) {
					// TODO return inside finally: is this ok??
					return Operation.failure(aE);
				}
			}
		}
	}

	private Operation<CompilerInstructions> parseEzFile_(final EzSpec spec,
	                                                     final Compilation aCompilation) {
		final QueryEzFileToModuleParams qp = new QueryEzFileToModuleParams(spec, aCompilation);
		return new QueryEzFileToModule().calculate(qp);
	}
}
