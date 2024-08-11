package tripleo.elijah.comp.internal;

import java.io.*;
import java.util.*;

import org.jetbrains.annotations.*;

import tripleo.elijah.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.nextgen.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.*;
import tripleo.elijah.world.i.*;
import tripleo.wrap.File;

public enum CX_realParseElijjahFile2 {
	;

	/**
	 * 1. Get absolute path <br/>
	 * 2. Check cache <br/>
	 * 3. Parse, then cache <br/>
	 * 4. Create WorldModule and World#addModule2 <br/>
	 * 5. Return success <br/>
	 */
	public static Operation2<OS_Module> realParseElijjahFile2(final ElijahSpec spec,
			final @NotNull ElijahCache aElijahCache, final @NotNull Compilation aC) {
		final File file = spec.file();

		final String absolutePath;
		try {
			absolutePath = file.getCanonicalFile().toString();
		} catch (final IOException aE) {
			return Operation2.failure_exc(aE);
		}

		final Optional<OS_Module> early = aElijahCache.get(absolutePath);

		if (early.isPresent()) {
			return Operation2.success(early.get());
		}

		final Operation2<OS_Module> calm = CX_ParseElijahFile.parseAndCache(spec, aElijahCache, absolutePath, aC);

		if (!DebugFlags.MakeSense) {
			final WorldModule worldModule = aC.con().createWorldModule(calm.success());
			aC.world().addModule2(worldModule);
		}

		return calm;
	}
}
