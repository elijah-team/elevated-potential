package tripleo.elijah_elevateder.stages.deduce.nextgen;

import org.jetbrains.annotations.*;


import tripleo.elijah_fluffy.util.Eventual;
import tripleo.elijah_elevateder.stages.deduce.*;
import tripleo.elijah_elevateder.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_elevateder.stages.gen_fn.GeneratePhase;

/**
 * Pivot off of a {@link DeduceTypes2} instance to provide "Context" useful when creating things in the Deduce realm
 *
 * Chatty explanation:
 *
 * 1. Pivot means only one variable is retained
 *    a. the others could be stored as members for debugging (design decision)
 *    b. there is no reason for computation, as they should all be created (so JIT can inline ;)
 * 2. #makeGenerated_fi__Eventual is used in one place as of now
 *
 */
public class DefaultDeduceCreationContext implements DeduceCreationContext {
	private final DeduceTypes2 deduceTypes2;

	public DefaultDeduceCreationContext(DeduceTypes2 aDeduceTypes2) {
		deduceTypes2 = aDeduceTypes2;
	}

	@Override
	public @NotNull DeducePhase getDeducePhase() {
		return deduceTypes2.phase;
	}

	@Override
	public DeduceTypes2 getDeduceTypes2() {
		return deduceTypes2;
	}

	@Override
	public @NotNull GeneratePhase getGeneratePhase() {
		return getDeducePhase().generatePhase;
	}

	@Override
	public Eventual<BaseEvaFunction> makeGenerated_fi__Eventual(final @NotNull FunctionInvocation aFunctionInvocation) {
		return aFunctionInvocation.makeGenerated__Eventual(this, null);
	}
}
