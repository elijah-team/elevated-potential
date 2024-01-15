package tripleo.elijah.comp.local;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.graph.CM_Ez;
import tripleo.elijah.comp.i.CY_EzSpecParser;
import tripleo.elijah.comp.rubicon.PCon;
import tripleo.elijah.comp.specs.EzCache;
import tripleo.elijah.comp.specs.EzSpec;
import tripleo.elijah.comp.specs.EzSpec__;
import tripleo.elijah.diagnostic.ExceptionDiagnostic;
import tripleo.elijah.util.Mode;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.Operation2;
import tripleo.elijah.util2.Eventual;
import tripleo.elijjah.EzLexer;
import tripleo.elijjah.EzParser;
import tripleo.wrap.File;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

public enum CX_ParseEzFile {;
	public static class PEventual<T> extends Eventual<T> {

		@SuppressWarnings("unchecked")
		public T waitForIt() {
			Object xx[] = {null};
			this.then(x -> {
				xx[0]=x;
			});
			while (xx[0] == null) {
				try {
					System.err.println("33 33 33 33 33");
					Thread.sleep(5000L);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return (T) xx[0];
		}
	}
	
	public class CXX_Parser {
		private static PEventual<CompilerInstructions> _parseEz = new PEventual<>();

		public static PEventual<CompilerInstructions> parseEz(String aAbsolutePath, InputStream aInputStream) {
			return _parseEz;
		}
	}
	
	private static Operation2<CompilerInstructions> calculate(final String aAbsolutePath, final InputStream aInputStream) {
		final PEventual<CompilerInstructions> pez = CXX_Parser.parseEz(aAbsolutePath, aInputStream);
		
		Supplier<Operation2<CompilerInstructions>> x = new Supplier<Operation2<CompilerInstructions>>() {
			@Override
			public @NotNull Operation2<CompilerInstructions> get() {
				final EzLexer lexer = new EzLexer(aInputStream);
				lexer.setFilename(aAbsolutePath);
				final EzParser parser = new EzParser(lexer);
				parser.setFilename(aAbsolutePath);
				PCon.connectDefault(parser);
				try {
					parser.program();

					final CompilerInstructions instructions = parser.ci;
					instructions.setFilename(()-> aAbsolutePath);
					return Operation2.success(instructions);
				} catch (final RecognitionException | TokenStreamException aE) {
					return Operation2.failure(new ExceptionDiagnostic(aE));
				}
			}};
		
		final Operation2<CompilerInstructions> operation2 = x.get();
		pez.resolve(operation2.success());
		final CompilerInstructions result = pez
				.waitForIt();
		
		return operation2;
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
