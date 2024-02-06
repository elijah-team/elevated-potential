package tripleo.elijah.nextgen.inputtree;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.CompilerInput;
import tripleo.elijah.util.*;

public class EIT_InputTreeImpl implements EIT_InputTree {
	@Override
	public void addNode(CompilerInput i) {
		int y = 2; // CM_Module?? what else could it be?
	}

	@Override
	public void setNodeOperation(final @NotNull CompilerInput input, final Operation<?> operation) {
		@Nullable Object o = input.getExt(EIT_InputTreeImpl.class);
		if (o == null) {
			input.putExt(EIT_InputTreeImpl.class, /*new _Node*/(operation));
		} else {
			input.putExt(EIT_InputTreeImpl.class, /*new _Node*/(operation));
		}
	}
}
