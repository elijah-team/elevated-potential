package tripleo.elijah.stages.gen_c;

import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.lang.i.OS_Type;
import tripleo.elijah.lang.i.TypeName;
import tripleo.elijah.nextgen.outputstatement.IReasonedString;
import tripleo.elijah.stages.gen_c.Generate_Code_For_Method.AOG;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah.stages.instructions.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

public abstract class WhyNotGarish_BaseFunction implements WhyNotGarish_Item {
	/**
	 * In places where this is used, change to "something else"
	 * @return
	 */
	@Deprecated
	public BaseEvaFunction cheat() {
		return getGf();
	}

	public abstract BaseEvaFunction getGf();

	public @Nullable Map<TypeName, OS_Type> classInvcationGenericPart() {
		return getGf().fi.getClassInvocation().genericPart().getMap();
	}

	public @Nullable Label findLabel(final int aIndex) {
		return getGf().findLabel(aIndex);
	}

	public @NotNull ConstantTableEntry getConstTableEntry(final int aIndex) {
		return getGf().getConstTableEntry(aIndex);
	}

	public @NotNull FunctionDef getFD() {
		return getGf().getFD();
	}

	public EvaNode getGenClass() {
		return getGf().getGenClass();
	}

	public @NotNull ProcTableEntry getProcTableEntry(final int aIndex) {
		return getGf().getProcTableEntry(aIndex);
	}

	public VariableTableEntry getSelf() {
		return getGf().getSelf();
	}

	public @NotNull TypeTableEntry getTypeTableEntry(final int aIndex) {
		return getGf().getTypeTableEntry(aIndex);
	}

	public @NotNull VariableTableEntry getVarTableEntry(final int aIndex) {
		return getGf().getVarTableEntry(aIndex);
	}

	public @NotNull List<Instruction> instructions() {
		return getGf().instructions();
	}

	public boolean pointsToConstructor() {
		return declaringContext().pointsToConstructor();
	}

	public boolean pointsToConstructor2() {
		return declaringContext().pointsToConstructor2();
	}

	@Override
	public abstract void provideFileGen(GenerateResultEnv fg);

	public @NotNull Pair<String, TypeTableEntry> tte_for_result() {
		@Nullable
		InstructionArgument result_index = getGf().vte_lookup("Result");
		if (result_index == null) {
			// if there is no Result, there should be Value
			result_index = getGf().vte_lookup("Value");
			// but Value might be passed in. If it is, discard value
			@NotNull final VariableTableEntry vte = ((IntegerIA) result_index).getEntry();
			if (vte.getVtt() != VariableTableType.RESULT)
				result_index = null;
			if (result_index == null)
				return Pair.of("void", null); // README Assuming Unit
		}

		// Get it from resolved
		var tte1 = getGf().getTypeTableEntry(((IntegerIA) result_index).getIndex());
		return Pair.of(null, tte1);
	}

	public @NotNull TypeTableEntry tte_for_self() {
		@Nullable final InstructionArgument result_index = getGf().vte_lookup("self");
		final IntegerIA resultIA = (IntegerIA) result_index;
		@NotNull final VariableTableEntry vte = resultIA.getEntry();
		assert vte.getVtt() == VariableTableType.SELF;

		var tte1 = getGf().getTypeTableEntry(resultIA.getIndex());

		return tte1;
	}

	public @Nullable InstructionArgument vte_lookup(final String aText) {
		return getGf().vte_lookup(aText);
	}

	public abstract WhyNotGarish_DeclaringContext declaringContext();

	public String getRealTargetName(final GenerateC gc, final IntegerIA target, final Generate_Code_For_Method.AOG aAOG) {
		return gc.getRealTargetName(getGf(), target, aAOG);
	}

	public String getAssignmentValue(final GenerateC gc, final InstructionArgument value) {
		var gf = getGf();
		final String assignmentValue = gc.getAssignmentValue(gf.getSelf(), value, gf);
		return assignmentValue;
	}

	//public String getRealTargetName(final GenerateC gc, final IdentIA target, final Generate_Code_For_Method.AOG aAOG, final String assignmentValue) {
	//	final ZoneITE zi = gc._zone.get(target);
	//
	//	return zi.getRealTargetName2(aAOG, assignmentValue);
	//}

	public Garish_TargetName getRealTargetName(final GenerateC gc, final IdentIA target, final String assignmentValue) {
		final ZoneITE zi = gc._zone.get(target);

		return zi.getRealTargetName3(assignmentValue);
	}

	public IReasonedString getRealTargetNameReasonedString(final GenerateC aGc, final IdentIA aTarget, final String aAssignmentValue, final String aReason, AOG aAOG) {
		Garish_TargetName text = getRealTargetName(aGc, aTarget, aAssignmentValue);
		return text.reasonedForAOG(aAOG);
	}
}
