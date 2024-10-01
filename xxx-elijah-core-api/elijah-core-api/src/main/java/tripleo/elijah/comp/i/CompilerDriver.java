package tripleo.elijah.comp.i;

import org.jetbrains.annotations.*;
import tripleo.elijah_fluffy.util.Operation;

public interface CompilerDriver {
	@NotNull Operation<CompilerDriven> get(DriverToken aToken);
}
