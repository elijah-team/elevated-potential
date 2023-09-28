package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.diagnostic.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.nextgen.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.util.function.*;

class CY_FindPrelude {
	private final ErrSink     errSink;
	private final ElijahCache elijahCache;
	private final Compilation c;

	CY_FindPrelude(final ErrSink aErrSink1,
	               final Supplier<Compilation> _c,
	               final Supplier<ElijahCache> _elijahCache) {
		errSink     = aErrSink1;
		c           = _c.get();
		elijahCache = _elijahCache.get();
	}

	@NotNull
	private static File local_prelude_file(final String prelude_name) {
		return new File("lib_elijjah/lib-" + prelude_name + "/Prelude.elijjah");
	}

	public static File __local_prelude_file(final String aPreludeName) {
		return local_prelude_file(aPreludeName);
	}

	public Operation2<OS_Module> findPrelude(final String prelude_name) {
		final File local_prelude = local_prelude_file(prelude_name);

		try {
			CY_ElijahSpecParser esp = c.con().defaultElijahSpecParser(elijahCache);
			return CX_ParseElijahFile.__parseEzFile(local_prelude.getName(),
			                                        local_prelude,
			                                        c.getIO(),
													esp
			);
		} catch (IOException e) {
			errSink.exception(e);
			return Operation2.failure(new ExceptionDiagnostic(e));
		}
	}
}
