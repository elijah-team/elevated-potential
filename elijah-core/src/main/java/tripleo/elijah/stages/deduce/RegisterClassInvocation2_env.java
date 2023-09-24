package tripleo.elijah.stages.deduce;

import tripleo.elijah.stages.gen_fn.GenerateFunctions;
import tripleo.elijah.work.WorkList;
import tripleo.elijah.work.WorkManager;

import java.util.function.Supplier;

public record RegisterClassInvocation2_env(
		RegisterClassInvocation_env env1,
		WorkManager workManager,
		WorkList workList,
		Supplier<GenerateFunctions> getGenerateFunctions
) { }
