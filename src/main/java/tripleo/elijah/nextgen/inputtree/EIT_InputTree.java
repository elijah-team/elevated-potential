package tripleo.elijah.nextgen.inputtree;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.inputs.CompilerInput;
import tripleo.elijah.util.*;
import tripleo.elijah_elevated.comp.model.CM_Resource;
import tripleo.elijah_elevated.comp.model.CM_ResourceCompute2;

public interface EIT_InputTree {
	void addNode(CompilerInput i);

	void addResourceNode(CM_Resource aResource, CM_ResourceCompute2 aCompute2);
}
