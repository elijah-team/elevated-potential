package tripleo.elijah.comp.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.util.*;

public interface CompilerDriver {
	@NotNull Operation<CompilerDriven> get(DriverToken aToken);
}
