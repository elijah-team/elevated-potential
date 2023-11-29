package tripleo.elijah.comp.nextgen;

import antlr.*;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.Asseverate;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.util.*;
import tripleo.elijjah.*;

import java.io.*;

public enum CX_ParseEzFile {;
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

	public static Operation2<CompilerInstructions> parseAndCache(final EzSpec__ aSpec,
	                                                            final EzCache aEzCache,
	                                                            final String absolutePath) {
		final Operation2<CompilerInstructions> cio = calculate(aSpec.file_name(), aSpec.sis().get());

		if (cio.mode() == Mode.SUCCESS) {
			final CompilerInstructions R = cio.success();
			aEzCache.put(aSpec, absolutePath, R);

			var c = aEzCache.getCompilation();
			c.getObjectTree().asseverate(Triple.of(aSpec, cio, R), Asseverate.CI_SPECCED);
			c.getObjectTree().asseverate(R, Asseverate.CI_PARSED);
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
			EzSpec__ spec = null;

			assert false;

			return Operation.convert(parser.parse(spec));
		} catch (final IOException aE) {
			return Operation.failure(aE);
		}
	}
}
