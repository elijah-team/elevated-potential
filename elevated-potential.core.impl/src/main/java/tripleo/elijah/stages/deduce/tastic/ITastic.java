package tripleo.elijah.stages.deduce.tastic;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.Context;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.IdentTableEntry;
import tripleo.elijah.stages.gen_fn.VariableTableEntry;
import tripleo.elijah.stages.instructions.FnCallArgs;
import tripleo.elijah.stages.instructions.Instruction;

public interface ITastic {
	void do_assign_call(@NotNull BaseEvaFunction generatedFunction,
						@NotNull Context ctx,
						@NotNull IdentTableEntry idte,
						@NotNull FnCallArgs fca,
						@NotNull Instruction instruction);

	void do_assign_call(BaseEvaFunction aGeneratedFunction,
						Context aContext,
						VariableTableEntry aVte,
						Instruction aInstruction,
						final OS_Element aName);
}
