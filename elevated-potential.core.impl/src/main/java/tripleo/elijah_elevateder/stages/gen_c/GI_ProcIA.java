package tripleo.elijah_elevateder.stages.gen_c;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.i.CompProgress;
import tripleo.elijah_fluffy.diagnostic.Diagnostic;
import tripleo.elijah_fluffy.diagnostic.Diagnostic.Severity;
import tripleo.elijah_fluffy.diagnostic.ExceptionDiagnostic;
import tripleo.elijah.lang.i.IdentExpression;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah_fluffy.util.Operation2;
import tripleo.elijah_elevateder.stages.deduce.ClassInvocation;
import tripleo.elijah_elevateder.stages.deduce.FunctionInvocation;
import tripleo.elijah_elevateder.stages.deduce.post_bytecode.DeduceElement3_ProcTableEntry;
import tripleo.elijah_elevateder.stages.gen_fn.*;
import tripleo.elijah_elevateder.stages.instructions.*;
import tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Consumer;

class GI_ProcIA implements GenerateC_Item {
	private final          GenerateC      gc;
	private final @NotNull ProcTableEntry pte;
	private                EvaNode        _evaNode;
	private final ProcIA  carrier;

	public GI_ProcIA(final ProcIA aProcIA, final GenerateC aGenerateC) {
		carrier = aProcIA;
		this.gc = aGenerateC;
		this.pte = carrier.getEntry();
	}

	public @NotNull Operation2<EG_Statement> action_CONSTRUCT(@NotNull Instruction aInstruction,
			@NotNull GenerateC gc) {
		final ProcTableEntry pte = carrier.getEntry();
		final List<TypeTableEntry> x = pte.getArgs();
		final int y = aInstruction.getArgsSize();
//		InstructionArgument z = instruction.getArg(1);

		final ClassInvocation clsinv = pte.getClassInvocation();
		if (pte.__debug_expression instanceof IdentExpression ie)
			if (ie.getText().equals("f"))
				return Operation2.failure(new ExceptionDiagnostic(new Exception("pte.expression is f")));

		if (clsinv != null) {
			final InstructionArgument target = pte.expression_num;
//			final InstructionArgument value  = instruction;

			if (target instanceof IdentIA) {
				// how to tell between named ctors and just a path?
				@NotNull
				final IdentTableEntry target2 = ((IdentIA) target).getEntry();
				final String str = target2.getIdent().getText();

				SimplePrintLoggerToRemoveSoon.println_out_4("130  " + str);
			}

			final String s = MessageFormat.format("{0}{1};", Emit.emit("/*500*/"),
					getAssignmentValue(aInstruction, gc));

			return Operation2.success(new EG_SingleStatement(s, EX_Explanation.withMessage("aaa")));
		}

		return Operation2.failure(Diagnostic.withMessage("12900", "no construct possible for GI_Proc", Severity.INFO));
	}

	public String getAssignmentValue(final @NotNull Instruction aInstruction, final @NotNull GenerateC gc) {
		final BaseEvaFunction gf = carrier.generatedFunction();
		final ClassInvocation clsinv = carrier.getEntry().getClassInvocation();

		// return gc.getAssignmentValue(gf.getSelf(), aInstruction, clsinv, gf);

		GenerateC.GetAssignmentValue gav = gc.new GetAssignmentValue();
//		return gav.forClassInvocation(aInstruction, clsinv, gf, gc.LOG);

		InstructionArgument _arg0 = aInstruction.getArg(0);
		@NotNull
		ProcTableEntry pte = carrier.getEntry();

		final CtorReference reference = new CtorReference();
		reference.getConstructorPath(pte.expression_num, gf);
		final GetAssignmentValueArgsStatement ava = gav.getAssignmentValueArgs(aInstruction, gf, gc.LOG);
		@NotNull
		List<String> x = ava.stringList();
		reference.args(x);
		final String build = reference.build(clsinv);
		return build;
	}

	@Override
	public EvaNode getEvaNode() {
		return _evaNode;
	}

	public @Nullable String getIdentIAPath(final @NotNull Consumer<Pair<String, CReference.Ref>> addRef) {
		return getIdentIAPath_Proc(carrier.getEntry(), addRef);
	}

	public @Nullable String getIdentIAPath_Proc(final @NotNull ProcTableEntry pte,
			final @NotNull Consumer<Pair<String, CReference.Ref>> addRef) {
		final String[]           text = new String[1];
		final FunctionInvocation fi   = pte.getFunctionInvocation();

		if (fi == null) {
			SimplePrintLoggerToRemoveSoon.println_err_2("7777777777777777 fi getIdentIAPath_Proc " + pte);

			return null;// throw new IllegalStateException();
		}

		/* final */
		BaseEvaFunction                     generated = fi.getGenerated();
		final DeduceElement3_ProcTableEntry de_pte    = (DeduceElement3_ProcTableEntry) pte.getDeduceElement3();

		if (generated == null) {
			logProgress(6464, ""+fi.pte);

			final WlGenerateCtor wlgf = new WlGenerateCtor(
					de_pte.deduceTypes2().getGenerateFunctions(de_pte.getPrincipal().getContext().module()), fi, null,
					de_pte.deduceTypes2().phase.getCodeRegistrar());
			wlgf.run(null);
			generated = wlgf.getResult();

			for (IdentTableEntry identTableEntry : generated.idte_list) {
				identTableEntry._fix_table(de_pte.deduceTypes2(), de_pte.generatedFunction());
			}

			// throw new IllegalStateException();
		}

		if (generated instanceof EvaConstructor ec) {
			final WhyNotGarish_Constructor ac = gc.a_lookup(ec);

			final String constructorNameText = ac.getConstructorNameText();

			generated.onGenClass(genClass -> {
				text[0] = String.format("ZC%d%s", genClass.getCode(), constructorNameText);
				addRef.accept(Pair.of(text[0], CReference.Ref.CONSTRUCTOR));
			});
		} else {
			final IdentExpression functionName = generated.getFD().getNameNode();
			generated.onGenClass(genClass -> {
				text[0] = String.format("z%d%s", genClass.getCode(), functionName.getText());
				addRef.accept(Pair.of(text[0], CReference.Ref.FUNCTION));
			});
		}

		return text[0];
	}

	private void logProgress(final int aI, final String aS) {
		gc.ce.logProgress(CompProgress.GenerateC, Pair.of(aI, aS));
	}

	@Override
	public void setEvaNode(final EvaNode a_evaNaode) {
		_evaNode = a_evaNaode;
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
