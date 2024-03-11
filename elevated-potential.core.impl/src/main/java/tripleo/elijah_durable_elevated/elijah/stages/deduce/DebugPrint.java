package tripleo.elijah_durable_elevated.elijah.stages.deduce;

import tripleo.elijah.stages.deduce.declarations.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.declarations.DeferredMember;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.*;

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
