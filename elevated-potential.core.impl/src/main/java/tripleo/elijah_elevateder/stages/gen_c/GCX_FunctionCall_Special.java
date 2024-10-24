package tripleo.elijah_elevateder.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.IdentExpression;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah_elevateder.stages.deduce.DeduceElement;
import tripleo.elijah_elevateder.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah_elevateder.stages.gen_fn.ProcTableEntry;
import tripleo.elijah_elevateder.stages.instructions.IdentIA;
import tripleo.elijah_elevateder.stages.instructions.Instruction;
import tripleo.elijah_elevateder.stages.instructions.InstructionArgument;
import tripleo.elijah_elevateder.stages.instructions.IntegerIA;
import tripleo.elijah_fluffy.util.Helpers;

import java.util.List;
import java.util.Map;

public class GCX_FunctionCall_Special {
	private final GenerateC gc;
	private final BaseEvaFunction gf;
	private final Instruction instruction;
	private final ProcTableEntry pte;

	public GCX_FunctionCall_Special(final ProcTableEntry aPte, final @NotNull WhyNotGarish_BaseFunction aGf,
			final GenerateC aGc, final Instruction aInstruction) {
		pte = aPte;
		gf = aGf.cheat();
		gc = aGc;
		instruction = aInstruction;
	}

	public @NotNull String getText() {
		final StringBuilder sb = new StringBuilder();

		final Map<OS_Element, DeduceElement> e = gf.elements();

		CReference reference = null;
		if (pte.expression_num == null) {
			final int y = 2;
			final IdentExpression ptex = (IdentExpression) pte.__debug_expression;
			final String text = ptex.getText();
			@Nullable
			final InstructionArgument xx = gf.vte_lookup(text);
			final String xxx;
			if (xx != null) {
				xxx = gc.getRealTargetName(gf, (IntegerIA) xx, Generate_Code_For_Method.AOG.GET);
			} else {
				xxx = text;
				gc.LOG.err("xxx is null " + text);
			}
			sb.append(Emit.emit("/*460*/") + xxx);
		} else {
			final IdentIA ia2 = (IdentIA) pte.expression_num;
			reference = new CReference(gc.repo(), gc.ce);
			reference.getIdentIAPath(ia2, Generate_Code_For_Method.AOG.GET, null);
			final List<String> sl3 = gc.getArgumentStrings(gf, instruction);
			reference.args(sl3);
			final String path = reference.build();
			sb.append(Emit.emit("/*463*/") + path);
		}
		if (reference == null) {
			sb.append('(');
			final List<String> sl3 = gc.getArgumentStrings(gf, instruction);
			sb.append(Helpers.String_join(", ", sl3));
			sb.append(");");
		}

		return sb.toString();
	}
}
