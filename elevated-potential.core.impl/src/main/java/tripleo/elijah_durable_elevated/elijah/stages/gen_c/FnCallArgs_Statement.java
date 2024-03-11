package tripleo.elijah_durable_elevated.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.IdentExpression;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.util.Helpers;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.ProcTableEntry;
import tripleo.elijah_durable_elevated.elijah.stages.instructions.Instruction;

import java.util.List;

class FnCallArgs_Statement implements EG_Statement {
	private final GenerateC generateC;
	private final GenerateC.GetAssignmentValue getAssignmentValue;
	private final Instruction                  inst;
	private final BaseEvaFunction              gf;
	private final ProcTableEntry               pte;

	public FnCallArgs_Statement(final GenerateC aGenerateC, final GenerateC.GetAssignmentValue aGetAssignmentValue,
			final ProcTableEntry aPte, final Instruction aInst, final BaseEvaFunction aGf) {
		generateC = aGenerateC;
		getAssignmentValue = aGetAssignmentValue;
		pte = aPte;
		inst = aInst;
		gf = aGf;
	}

	@Override
	public @NotNull EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("FnCallArgs_Statement");
	}

	@Override
	public @NotNull String getText() {
		var z = new ReasonedStringListStatement();

		// VERIFY computed. immediate
		final IdentExpression ptex = (IdentExpression) pte.__debug_expression;

		// VERIFY template usage
		z.append(ptex.getText(), "pte-expression");

		// VERIFY template push
		z.append(Emit.emit("/*671*/"), "emit-code");
		z.append("(", "open-brace");

		// VERIFY alias evaluation
		final GetAssignmentValueArgsStatement ava = getAssignmentValue.getAssignmentValueArgs(inst, gf, generateC.LOG);
		final List<String> sll = ava.stringList();
		// VERIFY template usage
		z.append(Helpers.String_join(", ", sll), "get-assignment-value-args");

		// VERIFY template push
		z.append(")", "close-brace");

		// VERIFY EG_St: <here> && getText() -> <~>
		return z.getText();
	}
}
