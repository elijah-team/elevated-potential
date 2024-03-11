package tripleo.elijah_durable_elevated.elijah.stages.gen_c;

import tripleo.elijah_durable_elevated.elijah.stages.instructions.*;

class GCFM_Inst_AGNK {

	private final GenerateC                 gc;
	private final Generate_Code_For_Method  generateCodeForMethod;
	private final WhyNotGarish_BaseFunction gf;
	private final Instruction               instruction;

	public GCFM_Inst_AGNK(final Generate_Code_For_Method aGenerateCodeForMethod, final GenerateC aGc,
			final WhyNotGarish_BaseFunction aGf, final Instruction aInstruction) {
		generateCodeForMethod = aGenerateCodeForMethod;
		gc = aGc;
		gf = aGf;
		instruction = aInstruction;
	}

	public String getText() {
		final InstructionArgument target = instruction.getArg(0);
		final InstructionArgument value  = instruction.getArg(1);

		final String realTarget = gc.getRealTargetName(gf, (IntegerIA) target, Generate_Code_For_Method.AOG.ASSIGN);
		final String assignmentValue = gc.getAssignmentValue(gf.getSelf(), value, gf.cheat());
		final String s = String.format(Emit.emit("/*278*/") + "%s = %s;", realTarget, assignmentValue);

		return s;
	}
}
