package tripleo.elijah.comp.local;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.IO;
import tripleo.elijah.comp.IO_;
import tripleo.elijah.comp.graph.i.Asseverate;
import tripleo.elijah.comp.i.CY_ElijahSpecParser;
import tripleo.elijah.comp.inputs.CompilerInput;
import tripleo.elijah.comp.i.CompilationClosure;
import tripleo.elijah.comp.i.ILazyCompilerInstructions;
import tripleo.elijah.comp.inputs.ILazyCompilerInstructions_;
import tripleo.elijah.comp.internal.Out;
import tripleo.elijah.comp.queries.CompilerInstructions_Result;
import tripleo.elijah.comp.queries.QuerySearchEzFiles;
import tripleo.elijah.comp.rubicon.PConParser;
import tripleo.elijah.comp.specs.ElijahCache;
import tripleo.elijah.comp.specs.ElijahSpec;
import tripleo.elijah.comp.specs.ElijahSpec_;
import tripleo.elijah.compiler_model.CM_Factory;
import tripleo.elijah.compiler_model.CM_Filename;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.util.Maybe;
import tripleo.elijah.util.Mode;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.Operation2;
import tripleo.elijjah.ElijjahLexer;
import tripleo.elijjah.ElijjahParser;
import tripleo.wrap.File;

import java.io.IOException;
import java.io.InputStream;

import static tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err2;

public class CW {
	public static void CW_inputIsDirectory_(final CompilerInput aInput, final CompilationClosure aCompilationClosure, final File aF) {
		final File                        directory = aInput.getFileForDirectory();
		final QuerySearchEzFiles          q         = new QuerySearchEzFiles(aCompilationClosure);
		final CompilerInstructions_Result loci      = q.process(directory);

		aInput.setDirectoryResults(loci);
	}

	public static void CW_inputIsEzFile_(final CompilerInput aInput, final CompilationClosure aCompilationClosure) {
		final ILazyCompilerInstructions        ilci = ILazyCompilerInstructions_.of(aInput, aCompilationClosure);
		final Maybe<ILazyCompilerInstructions> m4   = Maybe.of(ilci);
		aInput.accept_ci(m4);
	}

	public static class CX_ParseElijahFile {

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
														  @NotNull CW.CX_ParseElijahFile.ElijahSpecReader r,
														  @NotNull CY_ElijahSpecParser parser) {
			final ElijahSpec             spec = new ElijahSpec_(file_name, file, r);
			return parser.parse(spec);
		}

		public interface ElijahSpecReader {
			@NotNull Operation<InputStream> get();
		}
	}
}
