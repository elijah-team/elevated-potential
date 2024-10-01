package tripleo.elijah_elevateder.stages.deduce.post_bytecode;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_elevateder.stages.gen_fn.GenType;

public interface setup_GenType_Action {
	void run(final @NotNull GenType gt, final @NotNull setup_GenType_Action_Arena arena);
}
