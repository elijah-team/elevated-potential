package tripleo.elijah_elevateder.comp.caches;

import tripleo.elijah.ci.*;
import tripleo.elijah_elevateder.comp.Compilation;
import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.specs.*;

import java.util.*;

public class DefaultEzCache implements EzCache {
	final         Map<String, CompilerInstructions> fn2ci = new HashMap<>();
	private final Compilation                       compilation;

	public DefaultEzCache(final Compilation aCompilation) {
		compilation = aCompilation;
	}

	@Override
	public Optional<CompilerInstructions> get(final String absolutePath) {
		if (fn2ci.containsKey(absolutePath)) {
			return Optional.of(fn2ci.get(absolutePath));
		}

		return Optional.empty();
	}

	@Override
	public void put(final EzSpec aSpec, final String aAbsolutePath, final CompilerInstructions aCompilerInstructions) {
		fn2ci.put(aAbsolutePath, aCompilerInstructions);
	}

	@Override public Compilation0 getCompilation() {
		return compilation;
	}
}
