package tripleo.elijah.stages.deduce.tastic;

import org.jetbrains.annotations.*;

import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.*;

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
