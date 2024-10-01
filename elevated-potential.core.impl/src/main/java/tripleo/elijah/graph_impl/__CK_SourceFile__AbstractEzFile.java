package tripleo.elijah.graph_impl;

import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.graph.i.Asseverate;
import tripleo.elijah.comp.graph.i.CK_SourceFile;
import tripleo.elijah.comp.i.CompProgress;
import tripleo.elijah.comp.i.CompilationClosure;
import tripleo.elijah.comp.nextgen.i.*;
import tripleo.elijah.comp.specs.EzCache;
import tripleo.elijah.comp.specs.EzSpec;
import tripleo.elijah_fluffy.diagnostic.ExceptionDiagnostic;
import tripleo.elijah.nextgen.inputtree.EIT_Input;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.util.*;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;
import tripleo.elijah_elevated.comp.input.CX_ParseEzFile;
import tripleo.elijah_elevateder.comp.Compilation;
import tripleo.elijah_fluffy.util.*;
import tripleo.wrap.File;

import java.io.PrintStream;
import java.util.Optional;
import java.util.regex.Pattern;

@SuppressWarnings("rawtypes")
abstract class __CK_SourceFile__AbstractEzFile implements CK_SourceFile {
	protected Compilation   compilation;
	protected CompilerInput input;

	/**
	 * - I don't remember what absolutePath is for - Cache doesn't add to QueryDB
	 * <p>
	 * STEPS ------
	 * <p>
	 * 1. Get absolutePath<br/>
	 * 2. Check cache, return early<br/>
	 * 3. Parse (Query is incorrect I think)<br/>
	 * 4. Cache new result<br/>
	 * </p>
	 *
	 * @param spec
	 * @param cache
	 * @return
	 */
	public static Operation2<CompilerInstructions> realParseEzFile(final EzSpec spec, final EzCache cache) {
		final Operation<String> op = spec.absolute1();

		if (op.mode() == Mode.FAILURE) {
			return Operation2.failure(new ExceptionDiagnostic(op.failure()));
		}

		var absolutePath = op.success();

		final Optional<CompilerInstructions> early = cache.get(absolutePath);

		if (early.isPresent()) {
			return Operation2.success(early.get());
		}

		final Operation2<CompilerInstructions> cio = CX_ParseEzFile.parseAndCache(spec, cache, absolutePath);
		return cio;
	}

	@Override
	public void associate(final CompilationClosure aCc) {
		compilation = (Compilation) aCc.getCompilation();
	}

	@Override
	public void associate(final CompilerInput aInput, final CompilationClosure aCc) {
		input       = aInput;
		compilation = (Compilation) aCc.getCompilation();
	}

	@Override
	public CompilerInput compilerInput() {
		return input;
	}

	@Override
	public EIT_Input input() {
		throw new UnintendedUseException();
	}

	@Override
	public EOT_OutputFile output() {
		throw new UnintendedUseException();
	}

	protected void asserverate() {
		if (getFileName() == null) return;
		if (compilation == null) return;

		final String            file_name = getFileName();
		final File              file      = getFile();
		final Operation<String> hash      = CA_getHashForFile.apply(file_name, file);

		System.err.println("959595");

		compilation.getObjectTree().asseverate(new Asseveration() {
			@Override
			public Object target() {
				return __CK_SourceFile__AbstractEzFile.this;
			}

			@Override
			public Asseverate code() {
				return Asseverate.CI_HASHED;
			}

			@Override
			public void onLogProgress(final Asseverable asseverable_ce) {
				// !!
				if (asseverable_ce instanceof final /*G*/CompilationEnclosure ce) {
					ce.logProgress2(CompProgress.Ez__HasHash, new AsseverationLogProgress() {
						@Override
						public void call(PrintStream out, PrintStream err) {
							out.printf("[-- Ez has HASH ] %s %s%n", file_name, hash.success());
						}
					});
				} else {
					throw new AssertionError();
				}
			}
		});
	}

	protected abstract File getFile();

	public abstract String getFileName();

	public static boolean isEzFile(String aFileName) {
		return Pattern.matches(".+\\.ez$", aFileName);
	}
}
