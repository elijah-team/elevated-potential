package tripleo.elijah.comp;

import antlr.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.query.*;
import tripleo.elijah.util.*;
import tripleo.elijjah.*;

import java.io.*;
import java.util.function.*;

import static tripleo.elijah.util.Stupidity.*;

public class CX_ParseElijahFile {

	public static Operation<OS_Module> parseAndCache(final ElijahSpec aSpec,
	                                                 final ElijahCache aElijahCache,
	                                                 final String absolutePath,
	                                                 final Compilation compilation) {
		final @NotNull Operation<OS_Module> calm;

		try {
			final IO              io       = compilation.getIO();
			final String          f        = aSpec.f();
			final File            file     = aSpec.file();
			final IO._IO_ReadFile readFile = io.readFile2(file);

			try (final InputStream s = readFile.getInputStream()) {
				calm = calculate(f, s, compilation, readFile.getLongPath1());

				if (calm.mode() == Mode.FAILURE) {
					final Exception e = calm.failure();
					assert e != null;

					println_err2(("parser exception: " + e));
					e.printStackTrace(System.err);
				} else {
					aElijahCache.put(aSpec, absolutePath, calm.success());
				}
			}

			compilation.getObjectTree().asseverate(aSpec, Asseverate.ELIJAH_PARSED);

			return calm;
		} catch (final IOException aE) {
			return Operation.failure(aE);
		}
	}

	private static Operation<OS_Module> calculate(final ElijahSpec spec, final Compilation compilation) {
		final var absolutePath = spec.getLongPath2(); // !!
		return calculate(spec.f(), spec.s(), compilation, absolutePath);
	}

	private static Operation<OS_Module> calculate(final String f,
	                                              final InputStream s,
	                                              final Compilation compilation,
	                                              final String absolutePath) {
		final ElijjahLexer lexer = new ElijjahLexer(s);
		lexer.setFilename(f);
		final ElijjahParser parser = new ElijjahParser(lexer);
		parser.out = new Out(f, compilation, false);
		parser.setFilename(f);
		try {
			parser.program();
		} catch (final RecognitionException | TokenStreamException aE) {
			return Operation.failure(aE);
		}
		final OS_Module module = parser.out.module();
		parser.out = null;

		var x = module.getFileName();
		if (x == null)
			module.setFileName(absolutePath);
		return Operation.success(module);
	}

	public static Operation2<OS_Module> __parseEzFile(String file_name,
	                                                  File file,
	                                                  Compilation c,
	                                                  Function<ElijahSpec, Operation2<OS_Module>> realParseElijjahFile) throws IOException {
		try (final InputStream s = c.getIO().readFile(file)) {
			final ElijahSpec spec = new ElijahSpec(file_name, file, s);
			return realParseElijjahFile.apply(spec);
		}
	}
}
