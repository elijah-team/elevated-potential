package tripleo.elijah.stages.deduce.nextgen;

import org.jetbrains.annotations.*;
import tripleo.elijah.util.Eventual;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.gen_fn.*;

public interface DeduceCreationContext {
	@NotNull DeducePhase getDeducePhase();

	DeduceTypes2 getDeduceTypes2();

	@NotNull GeneratePhase getGeneratePhase();

	Eventual<BaseEvaFunction> makeGenerated_fi__Eventual(FunctionInvocation aFunctionInvocation);
}
