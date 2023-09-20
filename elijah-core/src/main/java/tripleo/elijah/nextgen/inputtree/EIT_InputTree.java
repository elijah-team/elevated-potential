package tripleo.elijah.nextgen.inputtree;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.util.*;

public class EIT_InputTree {
	public void setNodeOperation(final CompilerInput input, final Operation<?> operation) {
		@Nullable Object o = input.getExt(EIT_InputTree.class);
		if (o == null) {
			input.putExt(EIT_InputTree.class, new _Node(operation));
		} else {
			input.putExt(EIT_InputTree.class, new _Node(operation));
		}
	}

	public void addNode(CompilerInput i) {
		int y=2;
	}

	// TODO 09/20 where is this used?
	public record _Node(Operation<?> operation) {
	}
}
