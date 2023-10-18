package tripleo.elijah.stages.deduce;

import tripleo.elijah.stages.gen_fn.GenerateFunctions;
import tripleo.elijah.work.WorkList;
import tripleo.elijah.work.WorkManager;

import java.util.function.Supplier;

public final class RegisterClassInvocation2_env {
	private final RegisterClassInvocation_env env1;
	private final WorkManager                 workManager;
	private final WorkList                    workList;
	private final Supplier<GenerateFunctions> getGenerateFunctions;

	public RegisterClassInvocation2_env(
			RegisterClassInvocation_env env1,
			WorkManager workManager,
			WorkList workList,
			Supplier<GenerateFunctions> getGenerateFunctions
									   ) {
		this.env1                 = env1;
		this.workManager          = workManager;
		this.workList             = workList;
		this.getGenerateFunctions = getGenerateFunctions;
	}

	public RegisterClassInvocation_env env1() {
		return env1;
	}

	public WorkManager workManager() {
		return workManager;
	}

	public WorkList workList() {
		return workList;
	}

	public Supplier<GenerateFunctions> getGenerateFunctions() {
		return getGenerateFunctions;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (RegisterClassInvocation2_env) obj;
		return Objects.equals(this.env1, that.env1) &&
				Objects.equals(this.workManager, that.workManager) &&
				Objects.equals(this.workList, that.workList) &&
				Objects.equals(this.getGenerateFunctions, that.getGenerateFunctions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(env1, workManager, workList, getGenerateFunctions);
	}

	@Override
	public String toString() {
		return "RegisterClassInvocation2_env[" +
				"env1=" + env1 + ", " +
				"workManager=" + workManager + ", " +
				"workList=" + workList + ", " +
				"getGenerateFunctions=" + getGenerateFunctions + ']';
	}
}
