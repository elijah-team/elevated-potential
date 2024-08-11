package tripleo.elijah.stages.deduce;

import java.util.*;
import java.util.regex.*;

import org.jetbrains.annotations.*;

import tripleo.elijah.comp.i.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.util.*;

class Implement_Calls_ {
	private final DeduceTypes2 deduceTypes2;
	private final @NotNull Context context;
	private final @NotNull BaseEvaFunction gf;
	private final @NotNull InstructionArgument i2;
	private final int pc;
	private final @NotNull ProcTableEntry pte;

	public Implement_Calls_(final DeduceTypes2 aDeduceTypes2, final @NotNull BaseEvaFunction aGf,
			final @NotNull Context aContext,
			final @NotNull InstructionArgument aI2, final @NotNull ProcTableEntry aPte, final int aPc) {
		deduceTypes2 = aDeduceTypes2;
		gf = aGf;
		context = aContext;
		i2 = aI2;
		pte = aPte;
		pc = aPc;
	}

	void action() {
		final IExpression pn1 = pte.__debug_expression;
		if (!(pn1 instanceof IdentExpression)) {
			throw new IllegalStateException("pn1 is not IdentExpression");
		}

		final String pn = ((IdentExpression) pn1).getText();
		boolean found = deduceTypes2.lookup_name_calls(context, pn, pte);
		if (found)
			return;

		final @Nullable String pn2 = SpecialFunctions.reverse_name(pn);
		if (pn2 != null) {
			// LOG.info("7002 "+pn2);
			found = deduceTypes2.lookup_name_calls(context, pn2, pte);
			if (found)
				return;
		}

		if (i2 instanceof IntegerIA) {
			found = action_i2_IntegerIA(pn, pn2);
		} else {
			found = action_dunder(pn);
		}

		if (!found) {
			pte.setStatus(BaseTableEntry.Status.UNKNOWN, null);
		}
	}

	private boolean action_i2_IntegerIA(@NotNull String pn, @Nullable String pn2) {
		boolean found;
		final @NotNull VariableTableEntry vte = gf.getVarTableEntry(DeduceTypes2.to_int(i2));
		final Context ctx = gf.getContextFromPC(pc); // might be inside a loop or something
		final String vteName = vte.getName();
		if (vteName != null) {
			found = action_i2_IntegerIA_vteName_is_null(pn, pn2, ctx, vteName);
		} else {
			found = action_i2_IntegerIA_vteName_is_not_null(pn, pn2, vte);
		}
		return found;
	}

	private boolean action_dunder(String pn) {
		assert Pattern.matches("__[a-z]+__", pn);
		// LOG.info(String.format("i2 is not IntegerIA (%s)",i2.getClass().getName()));
		//
		// try to get dunder method from class
		//
		IExpression exp = pte.getArgs().get(0).__debug_expression;
		if (exp instanceof IdentExpression) {
			return action_dunder_doIt(pn, (IdentExpression) exp);
		}
		return false;
	}

	private boolean action_i2_IntegerIA_vteName_is_null(@NotNull String pn, @Nullable String pn2,
			@NotNull Context ctx, @NotNull String vteName) {
		boolean found = false;
		if (SpecialVariables.contains(vteName)) {
			deduceTypes2.LOG.err("Skipping special variable " + vteName + " " + pn);
		} else {
			final LookupResultList lrl2 = ctx.lookup(vteName);
			// LOG.info("7003 "+vteName+" "+ctx);
			final @Nullable OS_Element best2 = lrl2.chooseBest(null);
			if (best2 != null) {
				found = deduceTypes2.lookup_name_calls(best2.getContext(), pn, pte);
				if (found)
					return true;

				if (pn2 != null) {
					found = deduceTypes2.lookup_name_calls(best2.getContext(), pn2, pte);
					if (found)
						return true;
				}

				getErrSink().reportError("Special Function not found " + pn);
			} else {
				throw new NotImplementedException(); // Cant find vte, should never happen
			}
		}
		return found;
	}

	private boolean action_i2_IntegerIA_vteName_is_not_null(@NotNull String pn, @Nullable String pn2,
			@NotNull VariableTableEntry vte) {
		final @NotNull List<TypeTableEntry> tt = deduceTypes2.getPotentialTypesVte(vte);
		if (tt.size() != 1) {
			return false;
		}
		final OS_Type x = tt.get(0).getAttached();
		assert x != null;
		switch (x.getType()) {
			case USER_CLASS -> {
				pot_types_size_is_1_USER_CLASS(pn, pn2, x);
				return true;
			}
			case BUILT_IN -> {
				final Context ctx2 = context;// x.getTypeName().getContext();
				try {
					@NotNull
					GenType ty2 = deduceTypes2.resolve_type(x, ctx2);
					pot_types_size_is_1_USER_CLASS(pn, pn2, ty2.getResolved());
					return true;
				} catch (ResolveError resolveError) {
					resolveError.printStackTrace();
					getErrSink().reportDiagnostic(resolveError);
					return false;
				}
			}
			default -> {
				assert false;
				return false;
			}
		}
	}

	private boolean action_dunder_doIt(String pn, IdentExpression exp) {
		final @NotNull IdentExpression identExpression = exp;
		@Nullable
		InstructionArgument vte_ia = gf.vte_lookup(identExpression.getText());
		if (vte_ia != null) {
			VTE_TypePromises.dunder(pn, (IntegerIA) vte_ia, pte, deduceTypes2);
			return true;
		}
		return false;
	}

	private void pot_types_size_is_1_USER_CLASS(@NotNull String pn, @Nullable String pn2, @NotNull OS_Type x) {
		boolean found;
		final Context ctx1 = x.getClassOf().getContext();

		found = deduceTypes2.lookup_name_calls(ctx1, pn, pte);
		if (found)
			return;

		if (pn2 != null) {
			found = deduceTypes2.lookup_name_calls(ctx1, pn2, pte);
		}

		if (!found) {
			// throw new NotImplementedException(); // TODO
			getErrSink().reportError("Special Function not found " + pn);
		}
	}

	private ErrSink getErrSink() {
		return deduceTypes2._errSink();
	}
}
