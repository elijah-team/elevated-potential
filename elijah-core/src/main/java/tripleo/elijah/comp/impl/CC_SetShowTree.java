package tripleo.elijah.comp.impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.CompilationChange;

public class CC_SetShowTree implements CompilationChange {
	private final boolean flag;

	public CC_SetShowTree(final boolean aB) {
		flag = aB;
	}

	@Override
	public void apply(final @NotNull Compilation0 c) {
		c.cfg().setShowTree(flag);
	}
}
