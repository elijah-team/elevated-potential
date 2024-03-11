package tripleo.elijah_durable_elevated.elijah.stages.gen_c;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.EvaNode;

interface ConstructorPathOp {
	@Nullable
	String getCtorName();

	@Nullable
	EvaNode getResolved();
}
