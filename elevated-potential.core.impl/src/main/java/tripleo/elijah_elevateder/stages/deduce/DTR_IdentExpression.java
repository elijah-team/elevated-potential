package tripleo.elijah_elevateder.stages.deduce;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevateder.stages.gen_fn.*;
import tripleo.elijah_elevateder.stages.instructions.IntegerIA;

public class DTR_IdentExpression {
	private final DeduceTypeResolve deduceTypeResolve;
	private final IdentExpression identExpression;
	private final BaseTableEntry  bte;
	private       GenType         genType;

	public DTR_IdentExpression(final DeduceTypeResolve aDeduceTypeResolve, final IdentExpression aIdentExpression,
			final BaseTableEntry aBte) {
		deduceTypeResolve = aDeduceTypeResolve;
		identExpression = aIdentExpression;
		bte = aBte;
	}

	private void q(final GenType result) {
		if (genType == result)
			return; // !!??
		genType.copy(result); // TODO genType = result?? because we want updates...
	}

	public void run(final IElementHolder eh, final GenType genType1) {
		this.genType = genType1;

		if (eh instanceof final @NotNull GenericElementHolderWithIntegerIA eh1) {
			final IntegerIA                   integerIA          = eh1.getIntegerIA();
			final @NotNull VariableTableEntry variableTableEntry = integerIA.getEntry();
			assert variableTableEntry == bte;
			variableTableEntry.typeResolvePromise().then(this::q);
		} else {
			// final DeduceLocalVariable dlv = ((VariableTableEntry) bte).dlv;
			// dlv.setDeduceTypes2(deduceTypeResolve.backlink.__dt2, null,
			// deduceTypeResolve.backlink.__gf);

			bte.typeResolvePromise().then(this::q);
		}
	}
}
