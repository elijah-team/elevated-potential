package tripleo.elijah_elevateder.stages.gen_generic;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_fluffy.util.Ok;

import java.util.function.Consumer;

public class DoubleLatch<T> {
	private final @NotNull Consumer<T> action;
	private boolean simple;
	private T tt;

	// private IincInsnNode action;

	@Contract(pure = true)
	public DoubleLatch(final @NotNull Consumer<T> aAction) {
		action = aAction;
	}

	public void notifyData(T att) {
		tt = att;
		if (simple && tt != null) {
			action.accept(tt);
		}
	}

	public void notifyLatch(final Ok ignored) {
		simple = true;
		if (simple && tt != null) {
			action.accept(tt);
		}
	}
}
