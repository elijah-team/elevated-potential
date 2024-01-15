package tripleo.elijah.comp.graph;

import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.graph.i.CK_ObjectTree;
import tripleo.elijah.comp.specs.EzSpec;
import tripleo.elijah.util.Operation2;

public interface CM_Ez {
	void advise(EzSpec aSpec);

	void advise(Operation2<CompilerInstructions> aCio);

	void advise(CK_ObjectTree aObjectTree);

	EzSpec getSpec();
}
