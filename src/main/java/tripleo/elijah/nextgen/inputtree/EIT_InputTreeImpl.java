package tripleo.elijah.nextgen.inputtree;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.inputs.CompilerInput;
import tripleo.elijah.util.*;
import tripleo.elijah_elevated.comp.model.CM_Resource;
import tripleo.elijah_elevated.comp.model.CM_ResourceCompute2;

import java.util.Objects;

public class EIT_InputTreeImpl implements EIT_InputTree {
	// TODO 09/20 where is this used?
	public static final class _Node {
		private final Operation<?> operation;

		public _Node(Operation<?> operation) {
			this.operation = operation;
		}

		public Operation<?> operation() {
			return operation;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (obj == null || obj.getClass() != this.getClass()) return false;
			var that = (_Node) obj;
			return Objects.equals(this.operation, that.operation);
		}

		@Override
		public int hashCode() {
			return Objects.hash(operation);
		}

		@Override
		public String toString() {
			return "_Node[" + "operation=" + operation + ']';
		}
	}

	@Override
	public void addNode(CompilerInput i) {
		int y = 2;
	}

	@Override
	public void setNodeOperation(final @NotNull CompilerInput input, final Operation<?> operation) {
		@Nullable Object o = input.getExt(EIT_InputTreeImpl.class);
		if (o == null) {
			input.putExt(EIT_InputTreeImpl.class, new _Node(operation));
		} else {
			input.putExt(EIT_InputTreeImpl.class, new _Node(operation));
		}
	}

	@Override public void addResourceNode(final CM_Resource aResource, final CM_ResourceCompute2 aResourceCompute) {
		System.err.println("IMPL-ME 24/01/06");
	}
}
