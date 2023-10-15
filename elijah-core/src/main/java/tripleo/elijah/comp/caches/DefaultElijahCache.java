package tripleo.elijah.comp.caches;

import tripleo.elijah.comp.specs.*;
import tripleo.elijah.lang.i.*;

import java.util.*;

public class DefaultElijahCache implements ElijahCache {
	private final Map<String, OS_Module> fn2m = new HashMap<>();

	@Override
	public Optional<OS_Module> get(final String aAbsolutePath) {
		if (fn2m.containsKey(aAbsolutePath)) {
			return Optional.of(fn2m.get(aAbsolutePath));
		}

		return Optional.empty();
	}

	@Override
	public void put(final ElijahSpec aSpec, final String aAbsolutePath, final OS_Module aModule) {
		fn2m.put(aAbsolutePath, aModule);
	}
}
