package tripleo.elijah.comp.caches;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.util2.Eventual;

import java.util.*;
import java.util.function.Consumer;

import org.jdeferred2.DoneCallback;

public class DefaultEzCache implements EzCache {
	private final Map<String, CompilerInstructions> fn2ci = new HashMap<>();
	private final Map<String, Eventual<PSCI>> fn2cipm = new HashMap<>();
	private final Compilation compilation;

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
	public void onPath(final String absolutePath, Consumer<PSCI> cpsci) {
		getP(absolutePath)
		.then(new DoneCallback<PSCI>() {
			@Override
			public void onDone(PSCI arg0) {
				cpsci.accept(arg0);
			}
		});
	}

	@Override
	public void put(final EzSpec aSpec, final String aAbsolutePath, final CompilerInstructions aCompilerInstructions) {
		fn2ci.put(aAbsolutePath, aCompilerInstructions);
		getP(aAbsolutePath)
			.resolve(new PSCI(aSpec, aCompilerInstructions));
	}
	
	private Eventual<PSCI> getP(String aAbsolutePath) {
		if (fn2cipm.containsKey(aAbsolutePath)) {
			return fn2cipm.get(aAbsolutePath);
		}
		final Eventual<PSCI> result = new Eventual<>();
		fn2cipm.put(aAbsolutePath, result);
		return result;
	}

	@Override public Compilation0 getCompilation() {
		return compilation;
	}
}
