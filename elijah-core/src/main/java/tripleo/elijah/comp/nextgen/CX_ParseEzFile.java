package tripleo.elijah.comp.nextgen;

import antlr.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.nextgen.query.*;
import tripleo.elijah.util.*;
import tripleo.elijjah.*;

import java.io.*;

public class CX_ParseEzFile {
	private static Operation<CompilerInstructions> calculate(final String aAbsolutePath, final InputStream aInputStream) {
		final EzLexer lexer = new EzLexer(aInputStream);
		lexer.setFilename(aAbsolutePath);
		final EzParser parser = new EzParser(lexer);
		parser.setFilename(aAbsolutePath);
		parser.pcon = new Compilation.PCon();
		parser.ci   = parser.pcon.newCompilerInstructionsImpl();
		try {
			parser.program();
		} catch (final RecognitionException | TokenStreamException aE) {
			return Operation.failure(aE);
		}
		final CompilerInstructions instructions = parser.ci;
		instructions.setFilename(aAbsolutePath);
		return Operation.success(instructions);
	}

	public static Operation<CompilerInstructions> parseAndCache(final EzSpec aSpec,
	                                                            final EzCache aEzCache,
	                                                            final String absolutePath) {
		final Operation<CompilerInstructions> cio = calculate(aSpec.file_name(), aSpec.s().get());

		if (cio.mode() == Mode.SUCCESS) {
			aEzCache.put(aSpec, absolutePath, cio.success());
		}

		return cio;
	}

	public static Operation<CompilerInstructions> parseEzFile(final @NotNull File aFile,
	                                                          final Compilation aCompilation) {
		try (final InputStream readFile = aCompilation.getIO().readFile(aFile)) {
			final Operation<CompilerInstructions> cio = calculate(aFile.getAbsolutePath(), readFile);
			return cio;
		} catch (final IOException aE) {
			return Operation.failure(aE);
		}
	}
}
