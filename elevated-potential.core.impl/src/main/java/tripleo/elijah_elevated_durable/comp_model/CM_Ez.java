package tripleo.elijah_elevated_durable.comp_model;

import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.graph.i.CK_ObjectTree;
import tripleo.elijah.comp.specs.EzSpec;
import tripleo.elijah_fluffy.util.Operation2;

public interface CM_Ez {
	void advise(EzSpec aSpec);

	void advise(Operation2<CompilerInstructions> aCio);

	void advise(CK_ObjectTree aObjectTree);
}
