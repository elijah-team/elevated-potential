package tripleo.elijah.comp.nextgen;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.PCon;
import tripleo.elijah.comp.graph.CM_Ez;
import tripleo.elijah.comp.i.CY_EzSpecParser;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.compiler_model.CM_Factory;
import tripleo.elijah.diagnostic.ExceptionDiagnostic;
import tripleo.elijah.util.*;
import tripleo.elijjah.EzLexer;
import tripleo.elijjah.EzParser;
import tripleo.wrap.File;

import java.io.IOException;
import java.io.InputStream;

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
		instructions.setFilename(CM_Factory.Filename__of(aAbsolutePath));
		return Operation2.success(instructions);
	}

	public static Operation2<CompilerInstructions> parseAndCache(final EzSpec aSpec,
	                                                            final EzCache aEzCache,
	                                                            final String absolutePath) {
		final Operation2<CompilerInstructions> cio = calculate(aSpec.file_name(), aSpec.sis().get());

		if (cio.mode() == Mode.SUCCESS) {
			final CompilerInstructions R = cio.success();
			aEzCache.put(aSpec, absolutePath, R);

			final CM_Ez cm = ((Compilation) aEzCache.getCompilation()).megaGrande(aSpec);
			cm.advise(cio);
			cm.advise(aEzCache.getCompilation().getObjectTree());
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
