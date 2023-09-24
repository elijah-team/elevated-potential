package tripleo.elijah.comp;

import antlr.*;
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

	public static Operation<OS_Module> parseAndCache(final ElijahSpec aSpec, final ElijahCache aElijahCache,
			final String absolutePath, final Compilation compilation) {
		final Operation<OS_Module> calm;
		try {
			calm = parseElijahFile_(aSpec, compilation, aElijahCache, new File(absolutePath), compilation);
		} catch (final IOException aE) {
			return Operation.failure(aE);
		}

		if (calm.mode() == Mode.SUCCESS) {
			aElijahCache.put(aSpec, absolutePath, calm.success());
		}

		return calm;
	}

	private static Operation<OS_Module> parseElijahFile(final ElijahSpec spec, final Compilation compilation) {
		final var absolutePath = new File(spec.f()).getAbsolutePath(); // !!
		return parseElijahFile(spec.f(), spec.s(), spec.do_out(), compilation, absolutePath);
	}

	public static Operation<OS_Module> parseElijahFile(final String f, final InputStream s, final boolean do_out,
			final Compilation compilation, final String absolutePath) {
		final ElijjahLexer lexer = new ElijjahLexer(s);
		lexer.setFilename(f);
		final ElijjahParser parser = new ElijjahParser(lexer);
		parser.out = new Out(f, compilation, do_out);
		parser.setFilename(f);
		try {
			parser.program();
		} catch (final RecognitionException | TokenStreamException aE) {
			return Operation.failure(aE);
		}
		final OS_Module module = parser.out.module();
		parser.out = null;

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
