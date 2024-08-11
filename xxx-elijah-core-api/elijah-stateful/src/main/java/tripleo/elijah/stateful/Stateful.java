package tripleo.elijah.stateful;

import org.jetbrains.annotations.*;

public interface Stateful {
	void mvState(@Nullable State aBeginState, @NotNull State aState);

	/**
	 * Set the internal state variable.
	 * No other effects
	 *
	 * @param aState
	 */
	void setState(final State aState);
}
