package tripleo.elijah_elevated_durable.names_impl;


import tripleo.elijah.lang.nextgen.names.i.EN_Understanding;
import tripleo.elijah_elevated_durable.lang_impl.FunctionDefImpl;

public class ENU_FunctionDefinition implements EN_Understanding {
	private final FunctionDefImpl carrier;

	public ENU_FunctionDefinition(final FunctionDefImpl aFunctionDef) {
		carrier = aFunctionDef;
	}

	public FunctionDefImpl getCarrier() {
		return carrier;
	}
}
