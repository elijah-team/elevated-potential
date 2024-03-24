package tripleo.elijah_durable_elevated.elijah.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.FunctionInvocation;
import tripleo.elijah_durable_elevated.elijah.stages.instructions.InstructionArgument;

public interface DT_Resolvable {
	static @NotNull DT_Resolvable from(@NotNull InstructionArgument ia) {
		return new DT_Resolvable() {
			@Override
			public @Nullable Object deduceItem() {
				return null;
			}

			@Override
			public @Nullable OS_Element element() {
				return null;
			}

			@Override
			public InstructionArgument instructionArgument() {
				return ia;
			}
		};
	}

	static @NotNull DT_Resolvable from(@NotNull InstructionArgument aInstructionArgument,
			/* @NotNull */ OS_Element aElement, FunctionInvocation aFunctionInvocation) {
		return new DT_Resolvable() {
			@Override
			public Object deduceItem() {
				return aFunctionInvocation;
			}

			@Override
			public OS_Element element() {
				return aElement;
			}

			@Override
			public InstructionArgument instructionArgument() {
				return aInstructionArgument;
			}
		};
	}

	@Nullable
	Object deduceItem();

	@Nullable
	OS_Element element();

	InstructionArgument instructionArgument();
}
