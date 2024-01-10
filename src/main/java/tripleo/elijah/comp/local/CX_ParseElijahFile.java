package tripleo.elijah.comp.local;

import antlr.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.rubicon.PConParser;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.compiler_model.CM_Factory;
import tripleo.elijah.compiler_model.CM_Filename;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.*;
import tripleo.elijjah.*;

import java.io.*;
import tripleo.wrap.File;

import static tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.*;

public class CX_ParseElijahFile {

	public static Operation2<OS_Module> parseAndCache(final ElijahSpec aSpec,
													  final ElijahCache aElijahCache,
													  final String absolutePath,
													  final Compilation compilation) {
		final @NotNull Operation2<OS_Module> calm;

		try {
			final IO               io       = compilation.getIO();
			final String           f        = aSpec.file_name();
			final File             file     = aSpec.file();
			final IO_._IO_ReadFile readFile = io.readFile2(file);

			try (final InputStream s = readFile.getInputStream()) {
				final CM_Filename cmf = CM_Factory.Filename__of(f);

				calm = calculate(cmf, s, compilation, readFile.getLongPath1());

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

			compilation.getObjectTree().asseverate(compilation.megaGrande(aSpec, calm), Asseverate.ELIJAH_PARSED);

			return calm;
		} catch (final IOException aE) {
			return Operation2.failure_exc(aE);
		}
	}

	private static Operation2<OS_Module> calculate(final CM_Filename cmf,
												   final InputStream s,
												   final Compilation compilation,
												   final String absolutePath) {
		final String f = cmf.getString(); // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee

		final ElijjahLexer lexer = new ElijjahLexer(s);
		lexer.setFilename(f);
		final ElijjahParser parser = new ElijjahParser(lexer);
		parser.out = new Out(f, compilation);
		parser.setFilename(f);

		parser.pcon = new PConParser();

		// README just saved for reference
		//  this is handled by out above and `parser.out.module'
		//parser.ci   = parser.pcon.newCompilerInstructionsImpl();

		try {
			parser.program();
		} catch (final RecognitionException | TokenStreamException aE) {
			return Operation2.failure_exc(aE);
		}
		final OS_Module module = parser.out.module();
		parser.out = null;

		final String x = module.getFileName();
		if (x == null) {
			assert false;
			module.setFileName(absolutePath); // TODO 09/26 you mentioned that this is a bug
		}
		return Operation2.success(module);
	}

	private static Operation2<OS_Module> calculate(final @NotNull ElijahSpec spec, final Compilation compilation) {
		final var absolutePath = spec.getLongPath2(); // !!
		var f = CM_Factory.Filename__of(spec.file_name()); // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
		return calculate(f, spec.getModule().s(), compilation, absolutePath);
	}

	public static Operation2<OS_Module> __parseEzFile(String file_name,
													  File file,
													  @NotNull ElijahSpecReader r,
													  @NotNull CY_ElijahSpecParser parser) {
		final ElijahSpec             spec = new ElijahSpec_(file_name, file, r);
		return parser.parse(spec);
	}

	public interface ElijahSpecReader {
		@NotNull Operation<InputStream> get();
	}
}
