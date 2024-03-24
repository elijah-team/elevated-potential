package tripleo.elijah_durable_elevated.elijah.lang.nextgen.names.impl;

import tripleo.elijah.lang.nextgen.names.i.EN_Understanding;
import tripleo.elijah_durable_elevated.elijah.lang.impl.FunctionDefImpl;

public class ENU_FunctionDefinition implements EN_Understanding {
	private final FunctionDefImpl carrier;

	public ENU_FunctionDefinition(final FunctionDefImpl aFunctionDef) {
		carrier = aFunctionDef;
	}

	public FunctionDefImpl getCarrier() {
		return carrier;
	}
}
