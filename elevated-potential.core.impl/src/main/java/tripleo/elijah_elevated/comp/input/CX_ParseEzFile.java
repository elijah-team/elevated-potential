package tripleo.elijah_elevated.comp.input;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.i.CY_EzSpecParser;
import tripleo.elijah.comp.specs.EzCache;
import tripleo.elijah.comp.specs.EzSpec;
import tripleo.elijah_fluffy.diagnostic.ExceptionDiagnostic;
import tripleo.elijah_elevated_durable.comp_model.CM_Ez;
import tripleo.elijah_elevated_durable.comp_model.CM_Factory;
import tripleo.elijah_elevated_durable.parser.PCon;
import tripleo.elijah_elevated_durable.parser.antlr2.EzLexer;
import tripleo.elijah_elevated_durable.parser.antlr2.EzParser;
import tripleo.elijah_elevateder.comp.i.Compilation;
import tripleo.elijah_elevateder.comp.specs.EDL_EzSpec;
import tripleo.elijah_fluffy.util.*;
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
		final @NotNull Operation<InputStream>  cis = aSpec.sis();
		final Operation2<CompilerInstructions> cio;

		if (cis.mode() == Mode.SUCCESS) {
			cio = calculate(aSpec.file_name_string(), cis.success());
			final CompilerInstructions R = cio.success();
			aEzCache.put(aSpec, absolutePath, R);

			final CM_Ez cm = ((Compilation) aEzCache.getCompilation()).megaGrande(aSpec);
			cm.advise(cio);
			cm.advise(aEzCache.getCompilation().getObjectTree());
		} else {
			cio = Operation2.failure(new ExceptionDiagnostic(cis.failure()));
		}
		return cio;
	}

	public static Operation<CompilerInstructions> parseEzFile(final @NotNull File aFile,
															  final Compilation aCompilation) {
		assert false;
		try (final InputStream readFile = aCompilation.getIO().readFile(aFile)) {

			// FIXME double conversion
			CY_EzSpecParser parser = new CY_EzSpecParser() {
				@Override
				public Operation2<CompilerInstructions> parse(final EzSpec spec) {
					final Operation2<CompilerInstructions> cio = calculate(aFile.getAbsolutePath(), readFile);
					return cio;
				}
			};
			EDL_EzSpec spec = null;

			assert false;

			return Operation.convert(parser.parse(spec));
		} catch (final IOException aE) {
			return Operation.failure(aE);
		}
	}

	public interface EzSpecReader {
		@NotNull Operation<InputStream> get();
	}
}
