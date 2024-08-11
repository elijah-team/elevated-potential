package tripleo.elijah.stages.deduce;

import org.jetbrains.annotations.*;

import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.logging.*;

public class DeduceClient2 {
	private final DeduceTypes2 deduceTypes2;

	public DeduceClient2(DeduceTypes2 deduceTypes2) {
		this.deduceTypes2 = deduceTypes2;
	}

	public DeduceTypes2 deduceTypes2() {
		return deduceTypes2;
	}

	public @NotNull ClassInvocation genCI(@NotNull GenType genType, TypeName typeName) {
		return genType.genCI(typeName, deduceTypes2, deduceTypes2._errSink(), deduceTypes2.phase);
	}

	public @NotNull ElLog getLOG() {
		return deduceTypes2.LOG;
	}

	public @NotNull FunctionInvocation newFunctionInvocation(FunctionDef constructorDef, ProcTableEntry pte,
			@NotNull IInvocation ci) {
		return deduceTypes2.newFunctionInvocation(constructorDef, pte, ci, deduceTypes2.phase);
	}

	public @Nullable ClassInvocation registerClassInvocation(@NotNull ClassInvocation ci) {
		RegisterClassInvocation_env env = new RegisterClassInvocation_env(ci, () -> deduceTypes2,
				() -> deduceTypes2.phase);

		return deduceTypes2.phase.registerClassInvocation(env);
	}

	public NamespaceInvocation registerNamespaceInvocation(NamespaceStatement namespaceStatement) {
		return deduceTypes2.phase.registerNamespaceInvocation(namespaceStatement);
	}
}
