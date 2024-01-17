package tripleo.elijah.nextgen.inputtree;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.util.*;

public interface EIT_InputTree {
	void addNode(CompilerInput i);

	void setNodeOperation(@NotNull CompilerInput input, Operation<?> operation);
}
