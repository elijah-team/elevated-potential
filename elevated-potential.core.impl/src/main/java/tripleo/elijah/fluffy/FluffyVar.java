package tripleo.elijah.fluffy;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah_fluffy.diagnostic.Locatable;
import tripleo.elijah.nextgen.composable.IComposable;

public interface FluffyVar {
	String name();

	IComposable nameComposable();

	@Nullable
	Locatable nameLocatable();

	FluffyVarTarget target();
}
