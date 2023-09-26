package tripleo.elijah.comp.nextgen;

import org.apache.commons.lang3.tuple.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.util.*;

import java.io.*;

public class CX_realParseEzFile2 {

	@NotNull
	public static Operation<CompilerInstructions> realParseEzFile(final @NotNull Compilation c,
	                                                              final @NotNull EzSpec ezSpec,
	                                                              final @NotNull EzCache ezCache) {
		var file = ezSpec.file();
		var s    = ezSpec.s();

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
			final Operation<CompilerInstructions> cio = CX_ParseEzFile.parseEzFile(new File(ezSpec.f()), c);

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
}
