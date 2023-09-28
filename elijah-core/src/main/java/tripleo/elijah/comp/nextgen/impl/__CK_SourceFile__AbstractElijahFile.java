package tripleo.elijah.comp.nextgen.impl;

import tripleo.elijah.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.nextgen.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;

abstract class __CK_SourceFile__AbstractElijahFile implements CK_SourceFile {
//	private final __CK_SourceFile__AbstractElijjahFile CKSourceFile__abstractElijahFile;
	protected     Compilation                     compilation;
	protected     CompilerInput                   input;

//	public __CK_SourceFile__AbstractElijjahFile(final __CK_SourceFile__AbstractElijjahFile aCKSourceFile__abstractElijahFile) {
//		CKSourceFile__abstractElijahFile = aCKSourceFile__abstractElijahFile;
//	}

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
	public static Operation2<OS_Module> realParseElijahFile(final ElijahSpec spec, final ElijahCache cache, final Compilation compilation) {
		final String absolutePath = spec.getLongPath2();

		final Optional<OS_Module> early = cache.get(absolutePath);

		if (early.isPresent()) {
			return Operation2.success(early.get());
		}

		final Operation2<OS_Module> om = CX_ParseElijahFile.parseAndCache(spec, cache, absolutePath, compilation);
		return om;
	}

	public static boolean isElijjahFile(String aFileName) {
		return Pattern.matches(".+\\.elijjah$", aFileName) || Pattern.matches(".+\\.elijah$", aFileName);
	}

	public Operation2<OS_Module> realParseElijahFile(final ElijahSpec spec, final ElijahCache cache) {
		return realParseElijahFile(spec, cache, compilation);
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
//		if (getFileName() == null) return;
//		if (compilation == null) return;
//
//		final String            file_name = getFileName();
//		final File              file      = getFile();
//		final Operation<String> hash      = new CA_getHashForFile().apply(file_name, file);
//
//		compilation.getObjectTree().asseverate(new Asseveration() {
//			@Override
//			public Object target() {
//				return CKSourceFile__abstractEzFile;
//			}
//
//			@Override
//			public Asseverate code() {
//				return Asseverate.CI_HASHED;
//			}
//
//			@Override
//			public void onLogProgress(CompilationEnclosure ce) {
//				ce.logProgress2(CompProgress.Ez__HasHash, new AssererationLogProgress() {
//					@Override
//					public void call(PrintStream out, PrintStream err) {
//						out.printf("[-- Ez has HASH ] %s %s%n", file_name, hash.success());
//					}
//				});
//			}
//		});
	}

	protected abstract File getFile();

	public abstract String getFileName();
}
