package tripleo.elijah.stages.deduce;

import java.util.*;

import org.jetbrains.annotations.*;

import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.deduce.post_bytecode.*;
import tripleo.elijah.stages.gen_fn.*;

public class Zero {
	private final DeduceTypes2 deduceTypes2;
	private final Map<Object, IDeduceElement3> l = new HashMap<>();

	public Zero(final DeduceTypes2 aDeduceTypes2) {
		deduceTypes2 = aDeduceTypes2;
	}

	// TODO search living classes?
	public @NotNull List<EvaClass> findClassesFor(ClassStatement classStatement) {
		List<EvaClass> c = deduceTypes2._inj().new_LinkedList__EvaClass();

		for (Map.Entry<Object, IDeduceElement3> entry : l.entrySet()) {
			if (entry.getKey() instanceof EvaFunction evaFunction) {
				if (evaFunction.getFD().getParent() == classStatement) {
					var cls = (EvaClass) entry.getValue().generatedFunction().getGenClass();

					assert cls.getKlass() == classStatement;

					c.add(cls);
				}
			}
		}

		return c;
	}

	public DeduceElement3_Function get(final DeduceTypes2 aDeduceTypes2, final BaseEvaFunction aGeneratedFunction) {
		if (l.containsKey(aGeneratedFunction)) {
			return (DeduceElement3_Function) l.get(aGeneratedFunction);
		}

		final DeduceElement3_Function de3 = deduceTypes2._inj().new_DeduceElement3_Function(aDeduceTypes2,
				aGeneratedFunction);
		l.put(aGeneratedFunction, de3);
		return de3;
	}

	public DeduceElement3_ProcTableEntry get(final ProcTableEntry pte, final BaseEvaFunction aGeneratedFunction,
			final DeduceTypes2 aDeduceTypes2) {
		if (l.containsKey(pte)) {
			return (DeduceElement3_ProcTableEntry) l.get(pte);
		}

		final DeduceElement3_ProcTableEntry de3 = deduceTypes2._inj().new_DeduceElement3_ProcTableEntry(pte,
				aDeduceTypes2,
				aGeneratedFunction);
		l.put(pte, de3);
		return de3;
	}

	public DeduceElement3_VariableTableEntry get(final VariableTableEntry vte,
			final BaseEvaFunction aGeneratedFunction) {
		if (l.containsKey(vte)) {
			return (DeduceElement3_VariableTableEntry) l.get(vte);
		}

		final DeduceElement3_VariableTableEntry de3 = deduceTypes2._inj().new_DeduceElement3_VariableTableEntry(vte,
				deduceTypes2, aGeneratedFunction);
		l.put(vte, de3);
		return de3;
	}

	public DeduceElement3_IdentTableEntry getIdent(final IdentTableEntry ite,
			final BaseEvaFunction aGeneratedFunction, final DeduceTypes2 aDeduceTypes2) {
		if (l.containsKey(ite)) {
			return (DeduceElement3_IdentTableEntry) l.get(ite);
		}

		final DeduceElement3_IdentTableEntry de3 = deduceTypes2._inj().new_DeduceElement3_IdentTableEntry(ite);
		de3.setDeduceTypes(aDeduceTypes2, aGeneratedFunction);
		l.put(ite, de3);
		return de3;
	}
}
