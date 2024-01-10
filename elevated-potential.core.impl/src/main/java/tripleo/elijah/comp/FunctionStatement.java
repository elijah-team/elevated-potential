package tripleo.elijah.comp;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;
import tripleo.elijah.lang.i.FormalArgListItem;
import tripleo.elijah.lang.i.OS_Element2;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah.stages.instructions.Instruction;

public class FunctionStatement implements EG_Statement {
	private final IEvaFunctionBase evaFunction;
	private       String           filename;

	public FunctionStatement(final IEvaFunctionBase aEvaFunction) {
		evaFunction = aEvaFunction;
	}

	@Override
	public @NotNull EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("FunctionStatement");
	}

	public @NotNull String getFilename(@NotNull IPipelineAccess pa) {
		// HACK 07/07 register if not registered
		final EvaFunction          v    = (EvaFunction) evaFunction;
		int                        code = v.getCode();
		final CompilationEnclosure ce   = pa.getCompilationEnclosure();

		if (code == 0) {
			final ICodeRegistrar cr = ce.getPipelineLogic().dp.getCodeRegistrar();
			cr.registerFunction1(v);

			code = v.getCode();
			assert code != 0;
		}

		filename = "F_" + evaFunction.getCode() + evaFunction.getFunctionName();
		return filename;
	}

	@Override
	public @NotNull String getText() {
		final StringBuilder sb = new StringBuilder();

		final String str = "FUNCTION %d %s %s\n".formatted(evaFunction.getCode(), evaFunction.getFunctionName(),
														   ((OS_Element2) evaFunction.getFD().getParent()).name());
		sb.append(str);

		final EvaFunction gf = (EvaFunction) evaFunction;

		sb.append("Instructions \n");
		for (Instruction instruction : (gf).instructionsList) {
			sb.append("\t" + instruction + "\n");
		}
		{
			// EvaFunction.printTables(gf);
			{
				for (FormalArgListItem formalArgListItem : gf.getFD().getArgs()) {
					sb.append("ARGUMENT " + formalArgListItem.name() + " " + formalArgListItem.typeName() + "\n");
				}
				sb.append("VariableTable \n");
				for (VariableTableEntry variableTableEntry : gf.vte_list) {
					sb.append("\t" + variableTableEntry + "\n");
				}
				sb.append("ConstantTable \n");
				for (ConstantTableEntry constantTableEntry : gf.cte_list) {
					sb.append("\t" + constantTableEntry + "\n");
				}
				sb.append("ProcTable     \n");
				for (ProcTableEntry procTableEntry : gf.prte_list) {
					sb.append("\t" + procTableEntry + "\n");
				}
				sb.append("TypeTable     \n");
				for (TypeTableEntry typeTableEntry : gf.tte_list) {
					sb.append("\t" + typeTableEntry + "\n");
				}
				sb.append("IdentTable    \n");
				for (IdentTableEntry identTableEntry : gf.idte_list) {
					sb.append("\t" + identTableEntry + "\n");
				}
			}
		}

		return sb.toString();
	}
}
