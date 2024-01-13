package tripleo.elijah_elevated.comp.model;

import tripleo.elijah.comp.Compilation;
import tripleo.elijah.nextgen.inputtree.EIT_InputType;

public interface CM_ResourceCompute2 {
	void compute(final Compilation c);

	EIT_InputType responsibleType();
}
