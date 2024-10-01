package tripleo.elijah_elevateder.stages.gen_c;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah_elevateder.stages.gen_fn.EvaNode;
import tripleo.elijah_elevateder.stages.gen_fn.VariableTableEntry;
import tripleo.elijah_elevateder.stages.instructions.IntegerIA;

public class IntegerIA_Ops {
	private class ConstructorPathOp1 implements ConstructorPathOp {
		private boolean _calculated = false;

		@Nullable
		EvaNode _resolved = null;

		private void calculate() {
			final VariableTableEntry vte = integerIA.getEntry();

			if (sSize == 1) {
				final EvaNode resolved = vte.getTypeTableEntry().resolved();
				if (resolved != null) {
					_resolved = resolved;
				} else {
					_resolved = vte.resolvedType();
				}
			}

			_calculated = true;
		}

		@Override
		public @Nullable String getCtorName() {
			return null;
		}

		@Override
		public EvaNode getResolved() {
			if (!_calculated) {
				calculate();
			}

			return _resolved;
		}
	}

	@Contract(value = "_, _ -> new", pure = true)
	public static @NotNull IntegerIA_Ops get(final IntegerIA aIntegerIA, final int aSSize) {
		return new IntegerIA_Ops(aIntegerIA, aSSize);
	}

	private final IntegerIA integerIA;

	private final int sSize;

	public IntegerIA_Ops(final IntegerIA aIntegerIA, final int aSSize) {
		integerIA = aIntegerIA;
		sSize = aSSize;
	}

	public @NotNull ConstructorPathOp getConstructorPath() {
		return new ConstructorPathOp1();
	}
}
