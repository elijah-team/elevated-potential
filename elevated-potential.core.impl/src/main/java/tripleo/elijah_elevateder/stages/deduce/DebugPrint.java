package tripleo.elijah_elevateder.stages.deduce;

import tripleo.elijah_elevateder.stages.deduce.declarations.DeferredMember;
import tripleo.elijah_elevateder.stages.gen_fn.*;

public class DebugPrint {
	public static void addDeferredMember(final DeferredMember aDm) {
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("**** addDeferredMember " + aDm);
	}

	public static void addDependentType(final BaseEvaFunction aGeneratedFunction, final GenType aGenType) {
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("**** addDependentType " + aGeneratedFunction + " " + aGenType);
	}

	public static void addPotentialType(final VariableTableEntry aVte, final ConstantTableEntry aCte) {
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("**** addPotentialType " + aVte + " " + aCte);
	}
}
