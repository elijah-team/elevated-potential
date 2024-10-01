package tripleo.elijah_elevateder.stages.deduce.nextgen;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_elevateder.stages.gen_fn.ProcTableEntry;

public class ProcedureCallUnderstanding implements DR_Ident.Understanding {
	private final ProcTableEntry procTableEntry;

	public ProcedureCallUnderstanding(final ProcTableEntry aProcTableEntry) {
		procTableEntry = aProcTableEntry;
	}

	@Override
	public @NotNull String asString() {
		return "ProcedureCallUnderstanding: " + procTableEntry.__debug_expression;
	}
}
