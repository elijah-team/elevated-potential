package tripleo.elijah.nextgen.inputtree;

import tripleo.elijah.comp.inputs.CompilerInput;
import tripleo.elijah_elevated.comp.model.CM_Resource;
import tripleo.elijah_elevated.comp.model.CM_ResourceCompute2;

public class EIT_InputTreeImpl implements EIT_InputTree {
	@Override
	public void addNode(CompilerInput i) {
		System.err.println("999-10 [EIT_InputTree::addNode] "+i);
	}

	@Override
	public void addResourceNode(final CM_Resource aResource, final CM_ResourceCompute2 aResourceCompute) {
		System.err.println("IMPL-ME 24/01/06");
	}
}
