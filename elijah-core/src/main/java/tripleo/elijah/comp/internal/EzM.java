package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.queries.*;
import tripleo.elijah.util.*;

import java.io.*;

import static tripleo.elijah.nextgen.query.Mode.*;

class EzM {
	// TODO pass to ce or (not pa or) something
	private void logProgress(final int code, final String message) {
		final String x = "[EzM] %d %s".formatted(code, message);
//		if (code == 27) return;
		System.out.println(x);
	}

	private Operation<CompilerInstructions> parseEzFile_(final String f, final InputStream s,
			final Compilation aCompilation) {
		final QueryEzFileToModuleParams qp = new QueryEzFileToModuleParams(f, s, aCompilation);
		return new QueryEzFileToModule(qp).calculate();
	}

	@NotNull
	Operation<CompilerInstructions> parseEzFile1(final @NotNull SourceFileParserParams p) {
		@NotNull
		final File f = p.f();

		logProgress(27, f.getAbsolutePath());

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
		final String f = p.file_name();
		final File file = p.f();

		try {
			final InputStream s = p.cc().io().readFile(file);
			final Operation<CompilerInstructions> oci = realParseEzFile(f, s, file, p.cc().getCompilation());

			if (/* false || */ oci.mode() == SUCCESS) {
				Operation<String> hash = new CA_getHashForFile().apply(p.file_name(), p.f());
				logProgress(166, hash.success());

				final CompilerInput input = p.input();

				// FIXME stdlib.ez will not get it's hash for example 07/03
				if (input != null) {
					input.accept_hash(hash.success());
				} else {
					System.err.println("***** 6262 " + f);

//					throw new NotImplementedException();
				}
			}

			return oci;
		} catch (FileNotFoundException aE) {
			return Operation.failure(aE);
		}
	}

	@NotNull
	Operation<CompilerInstructions> realParseEzFile(final String f, final @Nullable InputStream s,
			final @NotNull File file, final @NotNull Compilation c) {
		final String absolutePath;
		try {
			absolutePath = file.getCanonicalFile().toString(); // TODO 04/10 hash this and "attach"
			// queryDB.attach(compilerInput, new EzFileIdentity_Sha256($hash)); // ??
		} catch (IOException aE) {
			return Operation.failure(aE);
		}

		// TODO 04/10
		// Cache<CompilerInput, CompilerInstructions> fn2ci
		// /*EzFileIdentity??*/(MAP/*??*/, resolver is try stmt)
		if (c.fn2ci().containsKey(absolutePath)) { // don't parse twice
			// TODO 04/10
			// ...queryDB.attach(compilerInput, new EzFileIdentity_Sha256($hash)); // ??
			// fnci
			return Operation.success(c.fn2ci().get(absolutePath));
		}

		try {
			final Operation<CompilerInstructions> cio = parseEzFile_(f, s, c);

			if (cio.mode() != SUCCESS) {
				final Exception e = cio.failure();
				assert e != null;

				Stupidity.println_err_2(("parser exception: " + e));
				e.printStackTrace(System.err);
				// s.close();
				return cio;
			}

			final CompilerInstructions R = cio.success();
			R.setFilename(file.toString());
			c.fn2ci().put(absolutePath, R);
			return cio;
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
}
