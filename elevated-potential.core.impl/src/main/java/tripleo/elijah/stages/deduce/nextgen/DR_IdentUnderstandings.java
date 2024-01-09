package tripleo.elijah.stages.deduce.nextgen;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.deduce.post_bytecode.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.*;

public class DR_IdentUnderstandings {
	static class BacklinkUnderstanding implements DR_Ident.Understanding {
		private final InstructionArgument ia;

		public BacklinkUnderstanding(final InstructionArgument aInstructionArgument) {
			ia = aInstructionArgument;
		}

		@Override
		public String asString() {
			return String.format("BacklinkUnderstanding %s", ia);
		}
	}

	static class ClassUnderstanding implements DR_Ident.Understanding {
		private final DG_ClassStatement dcs;

		public ClassUnderstanding(final DG_ClassStatement aDG_classStatement) {
			dcs = aDG_classStatement;
		}

		@Override
		public @NotNull String asString() {
			return "ClassUnderstanding " + dcs.classInvocation();
		}
	}

	public static class ElementUnderstanding implements DR_Ident.Understanding {
		private final OS_Element x;

		public ElementUnderstanding(final OS_Element aX) {
			x = aX;
		}

		@Override
		public @NotNull String asString() {
			String xx = x.toString();

			if (x instanceof VariableStatement vs) {
				xx = vs.getName();
			}

			return "ElementUnderstanding " + xx;
		}

		public OS_Element getElement() {
			return x;
		}
	}

	static class PTEUnderstanding implements DR_Ident.Understanding {

		final ProcTableEntry pte;

		public PTEUnderstanding(final ProcTableEntry aPte) {
			pte = aPte;
		}

		@Override
		public String asString() {
			return String.format("PTEUnderstanding " + pte.__debug_expression);
		}
	}
}
