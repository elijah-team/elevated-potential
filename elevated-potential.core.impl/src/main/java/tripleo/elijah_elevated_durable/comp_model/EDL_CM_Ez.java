package tripleo.elijah_elevated_durable.comp_model;

import org.apache.commons.lang3.tuple.Triple;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.graph.i.Asseverate;
import tripleo.elijah.comp.graph.i.CK_ObjectTree;
import tripleo.elijah.comp.specs.EzSpec;
import tripleo.elijah_fluffy.util.Operation2;

public class EDL_CM_Ez implements CM_Ez {
	private EzSpec                           spec;
	private Operation2<CompilerInstructions> cio;

	@Override
	public void advise(final EzSpec aSpec) {
		this.spec = aSpec;
	}

	@Override
	public void advise(final Operation2<CompilerInstructions> aCio) {
		cio = aCio;
	}

	@Override
	public void advise(final CK_ObjectTree aObjectTree) {
		Object R = null;
		final Triple<EzSpec, Operation2<CompilerInstructions>, Object> T = Triple.of(spec, cio, R);
		aObjectTree.asseverate(T, Asseverate.CI_SPECCED);
		aObjectTree.asseverate(R, Asseverate.CI_PARSED);
	}
}
