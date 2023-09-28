package tripleo.elijah.comp.nextgen.impl;

import tripleo.elijah.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.diagnostic.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.nextgen.*;
import tripleo.elijah.comp.nextgen.i.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.nextgen.query.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.util.*;

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
		compilation = aCc.getCompilation();
	}

	@Override
	public void associate(final CompilerInput aInput, final CompilationClosure aCc) {
		input       = aInput;
		compilation = aCc.getCompilation();
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
		final Operation<String> hash      = new CA_getHashForFile().apply(file_name, file);

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
			public void onLogProgress(CompilationEnclosure ce) {
				ce.logProgress2(CompProgress.Ez__HasHash, new AssererationLogProgress() {
					@Override
					public void call(PrintStream out, PrintStream err) {
						out.printf("[-- Ez has HASH ] %s %s%n", file_name, hash.success());
					}
				});
			}
		});
	}

	protected abstract File getFile();

	public abstract String getFileName();

}
