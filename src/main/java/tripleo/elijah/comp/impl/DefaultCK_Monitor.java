package tripleo.elijah.comp.impl;

import tripleo.elijah.comp.graph.i.CK_Monitor;
import tripleo.elijah.util.UnintendedUseException;

public class DefaultCK_Monitor implements CK_Monitor {
	@Override
	public void reportSuccess() {
		throw new UnintendedUseException();
	}

	@Override
	public void reportFailure() {
		throw new UnintendedUseException();
	}
}
