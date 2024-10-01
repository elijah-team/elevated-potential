package tripleo.elijah_elevateder.stages.deduce.nextgen;

import org.jetbrains.annotations.*;
import tripleo.elijah.util.Eventual;
import tripleo.elijah_elevateder.stages.deduce.*;
import tripleo.elijah_elevateder.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_elevateder.stages.gen_fn.GeneratePhase;

public interface DeduceCreationContext {

	@NotNull DeducePhase getDeducePhase();

	DeduceTypes2 getDeduceTypes2();

	@NotNull GeneratePhase getGeneratePhase();

	Eventual<BaseEvaFunction> makeGenerated_fi__Eventual(FunctionInvocation aFunctionInvocation);

}
