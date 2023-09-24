package tripleo.elijah.stages.deduce.nextgen;

import org.jetbrains.annotations.*;
import tripleo.elijah.*;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.gen_fn.*;

public class DefaultDeduceCreationContext implements DeduceCreationContext {
	private final DeduceTypes2 deduceTypes2;

	public DefaultDeduceCreationContext(DeduceTypes2 aDeduceTypes2) {
		deduceTypes2 = aDeduceTypes2;
	}

	@Override
	@NotNull
	public DeducePhase getDeducePhase() {
		return deduceTypes2.phase;
	}

	@Override
	public DeduceTypes2 getDeduceTypes2() {
		return deduceTypes2;
	}

	@Override
	@NotNull
	public GeneratePhase getGeneratePhase() {
		return getDeducePhase().generatePhase;
	}

	@Override
	public Eventual<BaseEvaFunction> makeGenerated_fi__Eventual(final @NotNull FunctionInvocation aFunctionInvocation) {
		final GeneratePhase generatePhase = getGeneratePhase();
		final DeducePhase   deducePhase   = getDeducePhase();

		final Deduce_CreationClosure cl = new Deduce_CreationClosure(deducePhase, this, deduceTypes2, generatePhase);

		return aFunctionInvocation.makeGenerated__Eventual(cl, null);
	}
}
