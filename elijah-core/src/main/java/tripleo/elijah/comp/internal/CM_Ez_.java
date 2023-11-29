package tripleo.elijah.comp.internal;

import tripleo.elijah.comp.graph.CM_Ez;
import tripleo.elijah.comp.specs.EzSpec;

public class CM_Ez_ implements CM_Ez {
	private EzSpec spec;

	@Override
	public void advise(final EzSpec aSpec) {
		this.spec = aSpec;
	}
}
