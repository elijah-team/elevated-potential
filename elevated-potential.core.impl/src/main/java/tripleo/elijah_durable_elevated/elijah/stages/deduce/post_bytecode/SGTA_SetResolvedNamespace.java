package tripleo.elijah_durable_elevated.elijah.stages.deduce.post_bytecode;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.NamespaceStatement;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.GenType;

public class SGTA_SetResolvedNamespace implements setup_GenType_Action {
	private final NamespaceStatement namespaceStatement;

	@Contract(pure = true)
	public SGTA_SetResolvedNamespace(final NamespaceStatement aNamespaceStatement) {
		namespaceStatement = aNamespaceStatement;
	}

	@Override
	public void run(final @NotNull GenType gt, final @NotNull setup_GenType_Action_Arena arena) {
		gt.setResolvedn(namespaceStatement);
	}
}