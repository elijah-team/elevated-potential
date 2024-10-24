package tripleo.elijah_elevateder.stages.gen_generic;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah_elevateder.stages.gen_c.GenerateC;
import tripleo.elijah_fluffy.util.NotImplementedException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum OutputFileFactory {
	;

	private static Map<OS_Module, GenerateFiles> mgfMap = new HashMap<>();

	@Contract("_, _, _ -> new")
	public static @NotNull GenerateFiles create(final @NotNull String lang,
			final @NotNull OutputFileFactoryParams params, final GenerateResultEnv aFileGen) {
		if (Objects.equals(lang, "c")) {
			final OS_Module mod = params.getMod();

			if (mgfMap.containsKey(mod)) {
				return mgfMap.get(mod);
			}

			final GenerateC generateC = new GenerateC(params, aFileGen);
			mgfMap.put(mod, generateC);

			return generateC;
		} else
			throw new NotImplementedException();
	}
}
