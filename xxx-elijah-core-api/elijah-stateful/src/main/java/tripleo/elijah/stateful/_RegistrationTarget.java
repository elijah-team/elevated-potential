package tripleo.elijah.stateful;

import java.util.*;

import org.jetbrains.annotations.*;

public abstract class _RegistrationTarget {
	public State registerState(final @NotNull State aState) {
		if (!(registeredStates.contains(aState))) {
			registeredStates.add(aState);

			final int id = registeredStates.indexOf(aState);

			aState.setIdentity(new StateRegistrationToken(id));
			return aState;
		}

		return aState;
	}

	private final List<State> registeredStates = new ArrayList<>();
}
