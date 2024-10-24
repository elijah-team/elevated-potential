package tripleo.elijah_elevateder.stages.gen_c;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah_elevated_durable.lang_impl.LangGlobals;
import tripleo.elijah_elevateder.stages.deduce.DeduceTypes2;
import tripleo.elijah_elevateder.stages.deduce.FunctionInvocation;
import tripleo.elijah_elevateder.stages.deduce.post_bytecode.DeduceElement3_IdentTableEntry;
import tripleo.elijah_elevateder.stages.gen_fn.*;
import tripleo.elijah_elevateder.stages.instructions.*;

import java.util.List;

class FnCallArgs_Statement2 implements EG_Statement {
	private final GenerateC                    generateC;
	private final ProcTableEntry               pte;
	private final GenerateC.GetAssignmentValue getAssignmentValue;
	private final Instruction                  inst;
	private final BaseEvaFunction              gf;
	private final ElLog LOG;

	public FnCallArgs_Statement2(final GenerateC aGenerateC, final BaseEvaFunction aGf, final ElLog aLOG,
			final Instruction aInst, final ProcTableEntry aPte,
			final GenerateC.GetAssignmentValue aGetAssignmentValue) {
		generateC = aGenerateC;
		gf = aGf;
		LOG = aLOG;
		inst = aInst;
		pte = aPte;
		getAssignmentValue = aGetAssignmentValue;
	}

	@Override
	public @NotNull EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("FnCallArgs_Statement2");
	}

	@Override
	public @NotNull String getText() {
		final StringBuilder sb = new StringBuilder();

		var aGenerateC = generateC;

		if (pte.expression_num instanceof IntegerIA) {
		} else if (pte.expression_num instanceof IdentIA ia2) {
			final IdentTableEntry idte = ia2.getEntry();
			if (idte.getStatus() == BaseTableEntry.Status.UNCHECKED) {

				// final DeduceTypes2 deduceTypes2 = pte.getDeduceElement3().deduceTypes2();
				// final BaseEvaFunction gf1 = ia2.gf;

				final DeduceTypes2    deduceTypes2 = idte._deduceTypes2();
				final BaseEvaFunction gf1          = idte._generatedFunction();

				final DeduceElement3_IdentTableEntry de3_idte = idte.getDeduceElement3(deduceTypes2, gf1);
				de3_idte.sneakResolve();

			}

			if (idte.getStatus() == BaseTableEntry.Status.KNOWN) {
				final CReference         reference          = new CReference(aGenerateC._repo, aGenerateC.ce);
				final FunctionInvocation functionInvocation = pte.getFunctionInvocation();
				if (functionInvocation == null || functionInvocation.getFunction() == LangGlobals.defaultVirtualCtor) {
					reference.getIdentIAPath(ia2, Generate_Code_For_Method.AOG.GET, null);
					final GetAssignmentValueArgsStatement ava = getAssignmentValue.getAssignmentValueArgs(inst, gf,
							generateC.LOG);
					final List<String> sll = ava.stringList();
					reference.args(sll);
					String path = reference.build();
					sb.append(Emit.emit("/*829*/") + path);
				} else {
					final BaseEvaFunction pte_generated = functionInvocation.getEva();
					if (idte.resolvedType() == null && pte_generated != null)
						idte.resolveTypeToClass(pte_generated);
					reference.getIdentIAPath(ia2, Generate_Code_For_Method.AOG.GET, null);
					final GetAssignmentValueArgsStatement ava = getAssignmentValue.getAssignmentValueArgs(inst, gf,
							generateC.LOG);
					final List<String> sll = ava.stringList();
					reference.args(sll);
					String path = reference.build();
					sb.append(Emit.emit("/*827*/") + path);
				}
			} else {
				ZonePath zone_path = aGenerateC._zone.getPath(ia2);

				// 08/13 tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4("763 " + zone_path);

				final String path = gf.getIdentIAPathNormal(ia2);
				sb.append(Emit.emit("/*828*/") + String.format("%s is UNKNOWN", path));
			}
		}

		return sb.toString();
	}
}
