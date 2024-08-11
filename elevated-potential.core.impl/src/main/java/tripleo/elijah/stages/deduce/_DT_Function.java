package tripleo.elijah.stages.deduce;

import java.util.*;

import org.jetbrains.annotations.*;

import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.gen_fn.*;

class _DT_Function implements DT_Function {

	private final DeduceTypes2 deduceTypes2;
	private final BaseEvaFunction carrier;

	public _DT_Function(final DeduceTypes2 aDeduceTypes2, BaseEvaFunction aGeneratedFunction) {
		carrier = aGeneratedFunction;
		deduceTypes2 = aDeduceTypes2;
	}

	@Override
	public List<VariableTableEntry> vte_list() {
		return carrier.vte_list;
	}

	@Override
	public List<IdentTableEntry> idte_list() {
		return carrier.idte_list;
	}

	@Override
	public List<ProcTableEntry> prte_list() {
		return carrier.prte_list;
	}

	@Override
	public BaseEvaFunction get() {
		return carrier;
	}

	@Override
	public List<ConstantTableEntry> cte_list() {
		return carrier.cte_list;
	}

	@Override
	public void add_proc_table_listeners() {
		final @NotNull BaseEvaFunction generatedFunction = get();
		final __Add_Proc_Table_Listeners aptl = deduceTypes2._inj().new___Add_Proc_Table_Listeners();

		for (final @NotNull ProcTableEntry pte : generatedFunction.prte_list) {
			aptl.add_proc_table_listeners(generatedFunction, pte, deduceTypes2);
		}
	}

	@Override
	public void resolve_ident_table(final Context aContext) {
		for (@NotNull
		IdentTableEntry ite : this.idte_list()) {
			ite.resolveExpectation = deduceTypes2.promiseExpectation(ite, "Element Resolved");
			ite.addResolver(deduceTypes2._inj().new_Unnamed_ITE_Resolver1(deduceTypes2, ite, this.get(), aContext));
		}

		for (@NotNull
		IdentTableEntry ite : this.idte_list()) {
			ite.resolvers_round();
		}
	}

	@Override
	public void resolve_arguments_table(final Context aContext) {
		final @NotNull Resolve_Variable_Table_Entry rvte = deduceTypes2._inj().new_Resolve_Variable_Table_Entry(carrier,
				aContext, deduceTypes2);
		final @NotNull IVariableConnector connector = getVariableConnector();

		for (final @NotNull VariableTableEntry vte : vte_list()) {
			rvte.action(vte, connector);
		}
	}

	private @NotNull IVariableConnector getVariableConnector() {
		final @NotNull IVariableConnector connector;
		if (carrier instanceof EvaConstructor) {
			final IEvaConstructor evaConstructor = (IEvaConstructor) carrier;
			connector = deduceTypes2._inj().new_CtorConnector(evaConstructor, deduceTypes2);
		} else {
			connector = deduceTypes2._inj().new_NullConnector();
		}
		return connector;
	}

}
