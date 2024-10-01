package tripleo.elijah.comp.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah_fluffy.util.Operation2;

public interface ILazyCompilerInstructions {
	CompilerInstructions get();

	@Nullable
	Operation2<CompilerInstructions> getOperation();
}
