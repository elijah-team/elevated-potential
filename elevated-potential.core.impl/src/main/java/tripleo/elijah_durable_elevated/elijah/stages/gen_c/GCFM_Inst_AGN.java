package tripleo.elijah_durable_elevated.elijah.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.outputstatement.IReasonedString;
import tripleo.elijah.nextgen.outputstatement.ReasonedStringListStatement;
import tripleo.elijah_durable_elevated.elijah.stages.instructions.*;

import java.util.function.Supplier;

class GCFM_Inst_AGN implements GenerateC_Statement {

	private final GenerateC gc;
	//private final Generate_Code_For_Method generateCodeForMethod;
	private final WhyNotGarish_BaseFunction yf;
	private final Instruction               instruction;
	private       boolean                   _calculated;

	private String _calculatedText;

	public GCFM_Inst_AGN(final Generate_Code_For_Method aGenerateCodeForMethod,
						 final GenerateC aGc,
						 final WhyNotGarish_BaseFunction aGf,
						 final Instruction aInstruction) {
		//generateCodeForMethod = aGenerateCodeForMethod;
		gc                    = aGc;
		yf                    = aGf;
		instruction           = aInstruction;
	}

	@Override
	public String getText() {
		if (!_calculated) {
			final InstructionArgument target = instruction.getArg(0);
			final InstructionArgument value  = instruction.getArg(1);

			if (target instanceof IntegerIA) {
				final String realTarget = yf.getRealTargetName(gc, (IntegerIA) target, Generate_Code_For_Method.AOG.ASSIGN);
				final String assignmentValue = yf.getAssignmentValue(gc, value);

				var z = new ReasonedStringListStatement();
				z.append(Emit.emit("/*267*/"), "emit-code");
				z.append(realTarget, "real-target");
				z.append(" = ", "equals-sign");
				z.append(assignmentValue, "assignment-value");
				z.append(";", "closing-semi");

				_calculatedText = z.getText();
			} else {
				final String assignmentValue = yf.getAssignmentValue(gc, value);

				ReasonedStringListStatement zz = new ReasonedStringListStatement();
				zz.append(Emit.emit("/*501*/"), "emit-code");
				final IReasonedString reasonedString = yf.getRealTargetNameReasonedString(gc, (IdentIA) target, assignmentValue, "real-target", Generate_Code_For_Method.AOG.ASSIGN);
				zz.append(reasonedString);

				var z = new ReasonedStringListStatement();
				z.append(Emit.emit("/*249*/"), "emit-code");
				z.append(zz, "real-target");
				z.append(" = ", "equals-sign");
				z.append(assignmentValue, "assignment-value");
				z.append(";", "closing-semi");

				_calculatedText = z.getText();
			}
			_calculated = true;
		}

		assert _calculatedText != null;
		return _calculatedText;
	}

	public String getText2() {
		if (!_calculated) {
			var z = new ReasonedStringListStatement();

			final InstructionArgument target = instruction.getArg(0);
			final InstructionArgument value = instruction.getArg(1);

			if (target instanceof IntegerIA integerIA) {
				final Supplier<String> realTargetSupplier = () -> yf.getRealTargetName(gc, integerIA, Generate_Code_For_Method.AOG.ASSIGN);
				final Supplier<String> assignmentValueSupplier = () -> yf.getAssignmentValue(gc, value);

				z.append(realTargetSupplier, "real-target-name");
				z.append(assignmentValueSupplier, "assignment-value");

				_calculatedText = String.format(Emit.emit("/*267*/") + "%s = %s;", realTargetSupplier.get(),
						assignmentValueSupplier.get());
			} else {
				final Supplier<String> assignmentValueSupplier = () -> yf.getAssignmentValue(gc, value);
				final Supplier<String> s = () -> yf.getRealTargetName(gc, (IdentIA) target, assignmentValueSupplier.get()).forAOG(Generate_Code_For_Method.AOG.ASSIGN);

				final String realTargetName = s.get();

				final String s2 = Emit.emit("/*501*/") + realTargetName;

				_calculatedText = String.format(Emit.emit("/*249*/") + "%s = %s;", s2, assignmentValueSupplier.get());
			}
			_calculated = true;
		}

		return _calculatedText;
	}

	@Override
	public @NotNull GCR_Rule rule() {
		return GCR_Rule.withMessage("GCFM_Inst_AGN");
	}
}
