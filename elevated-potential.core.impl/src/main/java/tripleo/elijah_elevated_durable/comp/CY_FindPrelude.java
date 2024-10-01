package tripleo.elijah_elevated_durable.comp;

import tripleo.elijah.comp.i.CY_ElijahSpecParser;
import tripleo.elijah.comp.nextgen.i.CP_Path;
import tripleo.elijah.comp.nextgen.i.CP_StdlibPath;
import tripleo.elijah.comp.specs.ElijahCache;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.util.Operation2;
import tripleo.elijah_elevated.comp.input.CX_ParseElijahFile;
import tripleo.elijah_elevateder.comp.Compilation;

import java.util.function.Supplier;

public class CY_FindPrelude {
	private final Supplier<ElijahCache> _elijahCacheSupplier;
	private final Supplier<Compilation> _compilationSupplier;

	public CY_FindPrelude(final Supplier<Compilation> _c, final Supplier<ElijahCache> _elijahCache) {
		_compilationSupplier = _c;
		_elijahCacheSupplier = _elijahCache;
	}

	public Operation2<OS_Module> findPrelude(final String prelude_name) {
		final CP_Path             local_prelude = local_prelude(prelude_name);
		final CY_ElijahSpecParser                 esp = c().con().defaultElijahSpecParser(elijahCache());
		final CX_ParseElijahFile.ElijahSpecReader rdr = c().con().defaultElijahSpecReader(local_prelude);

		return CX_ParseElijahFile.__parseEzFile(local_prelude.getName(),
												local_prelude.toFile(),
												rdr,
												esp
											   );
	}

	public CP_Path local_prelude(final String prelude_name) {
		final CP_StdlibPath stdlibRoot = c().paths().stdlibRoot();
		final CP_Path       libdir     = stdlibRoot.child("lib-" + prelude_name);
		final CP_Path       prelude    = libdir.child("Prelude.elijjah");

		return prelude;
	}

	private Compilation c() {
		return _compilationSupplier.get();
	}

	private ElijahCache elijahCache() {
		return _elijahCacheSupplier.get();
	}
}
