package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.util.Operation;

import java.util.HashMap;
import java.util.Map;

public class CompilerDriver__ implements CompilerDriver {
	private final ICompilationBus cb;
	private final Map<DriverToken, CompilerDriven> defaults = new HashMap<>();
	private final Map<DriverToken, CompilerDriven> drivens = new HashMap<>();

	private /* static */ boolean initialized;

	public CompilerDriver__(final DefaultCompilationBus aDefaultCompilationBus) {
		cb = aDefaultCompilationBus;

		if (!initialized) {
			defaults.put(CompilationImpl.CompilationAlways.Tokens.COMPILATION_RUNNER_START,
						 new CD_CompilationRunnerStart_1());
			defaults.put(CompilationImpl.CompilationAlways.Tokens.COMPILATION_RUNNER_FIND_STDLIB2, new CD_FindStdLibImpl());
			initialized = true;
		}
	}

	@Override
	public @NotNull Operation<CompilerDriven> get(final DriverToken aToken) {
		final Operation<CompilerDriven> o;

		if (drivens.containsKey(aToken)) {
			o = Operation.success(drivens.get(aToken));
			return o;
		}

		if (defaults.containsKey(aToken)) {
			final CompilerDriven x = defaults.get(aToken);
			o = Operation.success(x);
		} else {
			o = Operation.failure(new Exception("Compiler Driven get failure"));
		}

		return o;
	}
}
