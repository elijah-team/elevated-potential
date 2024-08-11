package tripleo.elijah.stages.deduce;

import java.util.*;

import tripleo.elijah.stages.gen_fn.*;

class CtorConnector implements IVariableConnector {
	private final IEvaConstructor evaConstructor;
	private final DeduceTypes2 deduceTypes2;

	public CtorConnector(final IEvaConstructor aEvaConstructor, final DeduceTypes2 aDeduceTypes2) {
		evaConstructor = aEvaConstructor;
		deduceTypes2 = aDeduceTypes2;
	}

	@Override
	public void connect(final VariableTableEntry aVte, final String aName) {
		PromiseExpectation<EvaClass> pe_evaClass = deduceTypes2._inj().new_PromiseExpectation(evaConstructor,
				"evaConstructor.onGenClass", deduceTypes2);

		evaConstructor.onGenClass((EvaClass aGeneratedClass) -> {
			pe_evaClass.satisfy(aGeneratedClass);

			final List<VarTableEntry> vt = (aGeneratedClass).varTable;

			for (VarTableEntry gc_vte : vt) {
				if (gc_vte.nameToken.getText().equals(aName)) {
					gc_vte.connect(aVte, evaConstructor);
					break;
				}
			}
		});
	}
}
