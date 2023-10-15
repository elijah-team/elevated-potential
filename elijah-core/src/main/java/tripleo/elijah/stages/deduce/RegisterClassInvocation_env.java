package tripleo.elijah.stages.deduce;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public record RegisterClassInvocation_env(
		@NotNull ClassInvocation classInvocation,
		@NotNull Supplier<DeduceTypes2> deduceTypes2Supplier,
		@NotNull Supplier<DeducePhase> deducePhaseSupplier
) { }
