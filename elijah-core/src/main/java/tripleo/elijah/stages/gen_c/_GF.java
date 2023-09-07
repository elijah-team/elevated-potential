package tripleo.elijah.stages.gen_c;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.IExpression;
import tripleo.elijah.lang.i.IdentExpression;
import tripleo.elijah.nextgen.outputstatement.EG_CompoundStatement;
import tripleo.elijah.nextgen.outputstatement.EG_SingleStatement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_ProcTableEntry;
import tripleo.elijah.stages.deduce.post_bytecode.IDeduceElement3;
import tripleo.elijah.stages.gen_c.statements.ReasonedStringListStatement;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.ProcTableEntry;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.NotImplementedException;

import java.util.List;

import static tripleo.elijah.stages.gen_c.Generate_Code_For_Method.AOG.GET;

public enum _GF {
	;

	private static @NotNull EG_Statement forDeduceElement3_ProcTableEntry(@NotNull final DeduceElement3_ProcTableEntry de_pte, final @NotNull GenerateC gc) {
		final EG_SingleStatement beginning;
		final EG_SingleStatement ending;
		final EG_Statement       middle;
		final boolean            indent = false;
		final EX_Explanation     explanation;

		final ProcTableEntry pte = de_pte.getTablePrincipal();

		final BaseEvaFunction gf          = de_pte.getGeneratedFunction();
		final Instruction     instruction = de_pte.getInstruction();

		final EG_Statement sb = __Pte_Dispatch.dispatch(pte, new __Pte_Dispatch() {
			// README funny thing is, this is a class vv
			@Override
			public @NotNull EG_Statement statementForExpression(final IExpression expression) {
				var z = new ReasonedStringListStatement();

				final IdentExpression ptex = (IdentExpression) expression;
				final String          text = ptex.getText();

				@Nullable final InstructionArgument xx = gf.vte_lookup(text);
				assert xx != null;

				final List<String> sl3            = gc.getArgumentStrings(() -> new InstructionFixedList(instruction));

				z.append(Emit.emit("/*424*/"), "emit-code");
				z.append(() -> gc.getRealTargetName((IntegerIA) xx, GET), "real-target-name");
				z.append("(", "open-brace");
				z.append(Helpers.String_join(", ", sl3), "arguments");
				z.append(");", "close-brace");

				return z;
			}

			@Override
			public @NotNull EG_Statement statementForExpressionNum(final InstructionArgument expression_num) {
				var z = new ReasonedStringListStatement();

				z.append(Emit.emit("/*427-1*/"), "emit-code");
				z.append(() -> {
					final IdentIA identIA = (IdentIA) expression_num;

					final CReference reference = new CReference(gc.repo(), gc.ce);
					reference.getIdentIAPath(identIA, Generate_Code_For_Method.AOG.GET, null);
					final List<String> sl3 = gc.getArgumentStrings(() -> new InstructionFixedList(instruction));
					reference.args(sl3);
					final @NotNull String path = reference.build();

					return path;
				}, "path");
				z.append(";", "close-semi");

				return z;
			}
		});

		beginning   = new EG_SingleStatement("", EX_Explanation.withMessage("forDeduceElement3_ProcTableEntry >> beginning"));
		ending      = new EG_SingleStatement("", EX_Explanation.withMessage("forDeduceElement3_ProcTableEntry >> ending"));
		explanation = new EX_ProcTableEntryExplanation(de_pte);
		middle      = sb;

		final EG_CompoundStatement stmt = new EG_CompoundStatement(beginning, ending, middle, indent, explanation);
		return stmt;
	}

	interface __Pte_Dispatch {
		static EG_Statement dispatch(@NotNull final ProcTableEntry pte, final @NotNull _GF.__Pte_Dispatch xy) {
			if (pte.expression_num == null) {
				return xy.statementForExpression(pte.__debug_expression);
			} else {
				return xy.statementForExpressionNum(pte.expression_num);
			}
		}

		EG_Statement statementForExpression(IExpression expression);

		EG_Statement statementForExpressionNum(InstructionArgument expreesion_num);
	}
}
