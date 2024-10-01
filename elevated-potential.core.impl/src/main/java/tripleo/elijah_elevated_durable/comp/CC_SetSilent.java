package tripleo.elijah_elevated_durable.comp;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.i.CompilationChange;

public class CC_SetSilent implements CompilationChange {
	private final boolean flag;

	public CC_SetSilent(final boolean aB) {
		flag = aB;
	}

	@Override
	public void apply(final @NotNull Compilation0 c) {
		c.cfg().setSilent(flag);
	}
}
