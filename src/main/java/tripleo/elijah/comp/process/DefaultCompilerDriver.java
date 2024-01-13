package tripleo.elijah.comp.process;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.generated.CompilationAlways;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.util.Operation;

import java.util.HashMap;
import java.util.Map;

public class DefaultCompilerDriver implements CompilerDriver {
	@SuppressWarnings({"FieldCanBeLocal", "unused"})
	private final ICompilationBus                  cb;
	private final Map<DriverToken, CompilerDriven> defaults = new HashMap<>();
	@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
	private final Map<DriverToken, CompilerDriven> drivens  = new HashMap<>();

	@SuppressWarnings("BooleanVariableAlwaysNegated") // dont like this one
	private /* static */ boolean initialized;

	public DefaultCompilerDriver(final ICompilationBus aCompilationBus) {
		cb = aCompilationBus;

		if (!initialized) {
			defaults.put(CompilationAlways.Tokens.COMPILATION_RUNNER_START, new CD_CompilationRunnerStart_1());
			defaults.put(CompilationAlways.Tokens.COMPILATION_RUNNER_FIND_STDLIB2, new CD_FindStdLibImpl());
			initialized = true;
		} else assert false;
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
