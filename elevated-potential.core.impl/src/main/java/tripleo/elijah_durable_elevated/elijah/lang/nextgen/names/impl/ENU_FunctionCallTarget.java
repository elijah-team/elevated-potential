package tripleo.elijah_durable_elevated.elijah.lang.nextgen.names.impl;

import tripleo.elijah.lang.nextgen.names.i.EN_Understanding;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.ProcTableEntry;

public class ENU_FunctionCallTarget implements EN_Understanding {
	private final ProcTableEntry pte;

	public ENU_FunctionCallTarget(final ProcTableEntry aPte) {
		pte = aPte;
	}
}
