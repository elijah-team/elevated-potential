package tripleo.elijah.nextgen.reactive;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Convenience class to:
 * 
 * 1. Hold a list of {@link Reactivable}s
 * 2. Have them respond when {@code this} {@link Reactive} 
 *    gets notified that it is noticed by a {@link ReactiveDimension} 
 */
public abstract class DefaultReactive implements Reactive {
	private final @NotNull List<Reactivable> ables = new ArrayList<>();

	@Override
	public void add(final Reactivable aReactivable) {
		ables.add(aReactivable);
	}

	@Override
	public void join(final ReactiveDimension aDimension) {
		for (Reactivable reactivable : ables) {
			reactivable.respondTo(aDimension);
		}
	}
}
