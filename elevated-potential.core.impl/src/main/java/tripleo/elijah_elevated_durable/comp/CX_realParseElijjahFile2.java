package tripleo.elijah_elevated_durable.comp;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.specs.ElijahCache;
import tripleo.elijah.comp.specs.ElijahSpec;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah_fluffy.util.Operation2;
import tripleo.elijah_elevated.comp.input.CX_ParseElijahFile;
import tripleo.elijah_elevateder.DebugFlags;
import tripleo.elijah_elevateder.comp.Compilation;
import tripleo.elijah_elevateder.world.i.WorldModule;
import tripleo.wrap.File;

import java.io.IOException;
import java.util.Optional;

public enum CX_realParseElijjahFile2 {
	;

	/**
	 * 1. Get absolute path <br/>
	 * 2. Check cache <br/>
	 * 3. Parse, then cache <br/>
	 * 4. Create WorldModule and World#addModule2 <br/>
	 * 5. Return success <br/>
	 */
	public static Operation2<OS_Module> realParseElijjahFile2(final ElijahSpec spec, final @NotNull ElijahCache aElijahCache, final @NotNull Compilation aC) {
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
