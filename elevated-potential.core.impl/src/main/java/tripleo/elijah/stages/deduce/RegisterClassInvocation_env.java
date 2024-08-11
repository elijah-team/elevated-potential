package tripleo.elijah.stages.deduce;

import java.util.*;
import java.util.function.*;

import org.jetbrains.annotations.*;

public final class RegisterClassInvocation_env {
	private final @NotNull ClassInvocation classInvocation;
	private final @NotNull Supplier<DeduceTypes2> deduceTypes2Supplier;
	private final @NotNull Supplier<DeducePhase> deducePhaseSupplier;

	public RegisterClassInvocation_env(
			@NotNull ClassInvocation classInvocation,
			@NotNull Supplier<DeduceTypes2> deduceTypes2Supplier,
			@NotNull Supplier<DeducePhase> deducePhaseSupplier
									  ) {
		this.classInvocation      = classInvocation;
		this.deduceTypes2Supplier = deduceTypes2Supplier;
		this.deducePhaseSupplier  = deducePhaseSupplier;
	}

	public @NotNull ClassInvocation classInvocation() {
		return classInvocation;
	}

	public @NotNull Supplier<DeduceTypes2> deduceTypes2Supplier() {
		return deduceTypes2Supplier;
	}

	public @NotNull Supplier<DeducePhase> deducePhaseSupplier() {
		return deducePhaseSupplier;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (RegisterClassInvocation_env) obj;
		return Objects.equals(this.classInvocation, that.classInvocation) &&
				Objects.equals(this.deduceTypes2Supplier, that.deduceTypes2Supplier) &&
				Objects.equals(this.deducePhaseSupplier, that.deducePhaseSupplier);
	}

	@Override
	public int hashCode() {
		return Objects.hash(classInvocation, deduceTypes2Supplier, deducePhaseSupplier);
	}

	@Override
	public String toString() {
		return "RegisterClassInvocation_env[" +
				"classInvocation=" + classInvocation + ", " +
				"deduceTypes2Supplier=" + deduceTypes2Supplier + ", " +
				"deducePhaseSupplier=" + deducePhaseSupplier + ']';
	}
}
