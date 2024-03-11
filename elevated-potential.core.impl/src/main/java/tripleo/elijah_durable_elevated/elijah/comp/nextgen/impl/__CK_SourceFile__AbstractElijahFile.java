package tripleo.elijah_durable_elevated.elijah.comp.nextgen.impl;

import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.graph.i.CK_SourceFile;
import tripleo.elijah.comp.i.CompilationClosure;
import tripleo.elijah.comp.specs.ElijahCache;
import tripleo.elijah.comp.specs.ElijahSpec;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.inputtree.EIT_Input;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.util.Operation2;
import tripleo.elijah_durable_elevated.elijah.comp.Compilation;
import tripleo.elijah_durable_elevated.elijah.comp.nextgen.CK_GlobalRef;
import tripleo.elijah_durable_elevated.elijah.comp.nextgen.CX_ParseElijahFile;
import tripleo.wrap.File;

import java.util.Optional;
import java.util.regex.Pattern;

@SuppressWarnings("rawtypes")
abstract class __CK_SourceFile__AbstractElijahFile implements CK_SourceFile {
//	private final __CK_SourceFile__AbstractElijjahFile CKSourceFile__abstractElijahFile;
	protected CK_GlobalRef  compilation;
	protected CompilerInput input;

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
		return realParseElijahFile(spec, cache, compilation.asCompilation());
	}

	@Override
	public void associate(final CompilationClosure aCc) {
		compilation = (CK_GlobalRef) aCc.getCompilation();
	}

	@Override
	public void associate(final CompilerInput aInput, final CompilationClosure aCc) {
		input       = aInput;
		compilation = (CK_GlobalRef) aCc.getCompilation();
	}

	@Override
	public CompilerInput compilerInput() {
		return input;
	}

	@Override
	public EIT_Input input() {
		throw new UnintendedUseException("TODO 12/?? implement me");
	}

	@Override
	public EOT_OutputFile output() {
		throw new UnintendedUseException("TODO 12/?? implement me");
	}

	//protected void asserverate() {
	//}

	protected abstract File getFile();

	public abstract String getFileName();
}
