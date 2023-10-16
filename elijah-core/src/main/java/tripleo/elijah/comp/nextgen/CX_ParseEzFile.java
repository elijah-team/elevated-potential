package tripleo.elijah.comp.nextgen;

import antlr.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.util.*;
import tripleo.elijjah.*;

import java.io.*;

public class CX_ParseEzFile {
	private static Operation2<CompilerInstructions> calculate(final String aAbsolutePath, final InputStream aInputStream) {
		final EzLexer lexer = new EzLexer(aInputStream);
		lexer.setFilename(aAbsolutePath);
		final EzParser parser = new EzParser(lexer);
		parser.setFilename(aAbsolutePath);
		parser.pcon = new PCon();
		parser.ci   = parser.pcon.newCompilerInstructionsImpl();
		try {
			parser.program();
		} catch (final RecognitionException | TokenStreamException aE) {
			return Operation2.failure(new ExceptionDiagnostic(aE));
		}
		final CompilerInstructions instructions = parser.ci;
		instructions.setFilename(aAbsolutePath);
		return Operation2.success(instructions);
	}

	public static Operation2<CompilerInstructions> parseAndCache(final EzSpec aSpec,
	                                                            final EzCache aEzCache,
	                                                            final String absolutePath) {
		final Operation2<CompilerInstructions> cio = calculate(aSpec.file_name(), aSpec.sis().get());

		if (cio.mode() == Mode.SUCCESS) {
			aEzCache.put(aSpec, absolutePath, cio.success());
		}

		return cio;
	}

	public static Operation<CompilerInstructions> parseEzFile(final @NotNull File aFile,
	                                                          final Compilation aCompilation) {
		try (final InputStream readFile = aCompilation.getIO().readFile(aFile)) {

			// FIXME double conversion
			CY_EzSpecParser parser = new CY_EzSpecParser() {
				@Override
				public Operation2<CompilerInstructions> parse(final EzSpec spec) {
					final Operation2<CompilerInstructions> cio = calculate(aFile.getAbsolutePath(), readFile);
					return cio;
				}
			};
			EzSpec spec = null;

			return Operation.convert(parser.parse(spec));
		} catch (final IOException aE) {
			return Operation.failure(aE);
		}
	}
}
