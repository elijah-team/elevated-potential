package tripleo.elijah_elevateder.stages.deduce.post_bytecode;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah_fluffy.diagnostic.Diagnostic;
import tripleo.elijah_fluffy.diagnostic.Locatable;
import tripleo.elijah_fluffy.util.NotImplementedException;

import java.io.PrintStream;
import java.util.List;

public class ZeroPotentialDiagnostic implements Diagnostic {
	@Override
	public @Nullable String code() {
		NotImplementedException.raise();
		return null;
	}

	@Override
	public @NotNull Locatable primary() {
		NotImplementedException.raise();
		return null;
	}

	@Override
	public void report(final PrintStream stream) {
		NotImplementedException.raise();
		final int y = 2;
	}

	@Override
	public @NotNull List<Locatable> secondary() {
		NotImplementedException.raise();
		return null;
	}

	@Override
	public @Nullable Severity severity() {
		NotImplementedException.raise();
		return null;
	}
}
