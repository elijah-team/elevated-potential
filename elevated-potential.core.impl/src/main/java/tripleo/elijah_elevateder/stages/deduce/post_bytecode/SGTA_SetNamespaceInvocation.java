package tripleo.elijah_elevateder.stages.deduce.post_bytecode;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_elevateder.stages.deduce.NamespaceInvocation;
import tripleo.elijah_elevateder.stages.gen_fn.GenType;

public class SGTA_SetNamespaceInvocation implements setup_GenType_Action {
	@Override
	public void run(final @NotNull GenType gt, final @NotNull setup_GenType_Action_Arena arena) {
		final NamespaceInvocation nsi = arena.get("nsi");

		gt.setCi(nsi);
	}
}
