package tripleo.elijah.comp.nextgen;

import antlr.*;
import org.apache.commons.lang3.tuple.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.*;
import tripleo.elijjah.*;

import java.io.*;

import static tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err2;

public class CX_ParseElijahFile {

	public static Operation2<OS_Module> parseAndCache(final ElijahSpec aSpec,
	                                                 final ElijahCache aElijahCache,
	                                                 final String absolutePath,
	                                                 final Compilation compilation) {
		final @NotNull Operation2<OS_Module> calm;

		try {
			final IO              io       = compilation.getIO();
			final String          f        = aSpec.file_name();
			final File             file     = aSpec.file();
			final IO_._IO_ReadFile readFile = io.readFile2(file);

			try (final InputStream s = readFile.getInputStream()) {
				calm = calculate(f, s, compilation, readFile.getLongPath1());

				if (calm.mode() == Mode.FAILURE) {
					final Diagnostic failure = calm.failure();

					if (failure.get() instanceof Exception e) {
						assert e != null;

						println_err2(("parser exception: " + e));
						e.printStackTrace(System.err);
					} else {
						assert false;
					}
				} else {
					aElijahCache.put(aSpec, absolutePath, calm.success());
				}
			}


			compilation.getObjectTree().asseverate(Pair.of(aSpec,calm), Asseverate.ELIJAH_PARSED);

			return calm;
		} catch (final IOException aE) {
			return Operation2.failure_exc(aE);
		}
	}

	private static Operation2<OS_Module> calculate(final ElijahSpec spec, final Compilation compilation) {
		final var absolutePath = spec.getLongPath2(); // !!
		return calculate(spec.file_name(), spec.s(), compilation, absolutePath);
	}

	private static Operation2<OS_Module> calculate(final String f,
	                                               final InputStream s,
	                                               final Compilation compilation,
	                                               final String absolutePath) {
		final ElijjahLexer lexer = new ElijjahLexer(s);
		lexer.setFilename(f);
		final ElijjahParser parser = new ElijjahParser(lexer);
		parser.out = new Out(f, compilation, false);
		parser.setFilename(f);

		parser.pcon = new PConParser();
		//parser.ci   = parser.pcon.newCompilerInstructionsImpl(); // README just saved for reference

		try {
			parser.program();
		} catch (final RecognitionException | TokenStreamException aE) {
			return Operation2.failure_exc(aE);
		}
		final OS_Module module = parser.out.module();
		parser.out = null;

		final String x = module.getFileName();
		if (x == null)
			module.setFileName(absolutePath); // TODO 09/26 you mentioned that this is a bug
		return Operation2.success(module);
	}

	public static Operation2<OS_Module> __parseEzFile(String file_name,
	                                                  File file,
	                                                  IO io,
	                                                  @NotNull CY_ElijahSpecParser parser) throws IOException {
		try (final InputStream readFile = io.readFile(file)) {
			final ElijahSpec spec = new ElijahSpec_(file_name, file, readFile);
			return parser.parse(spec);
		}
	}
}
