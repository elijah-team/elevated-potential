package tripleo.elijah_elevated.comp.model;

import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.nextgen.comp_model.CM_CompilerInput;

public interface CM_ModelFactory {
	CM_CompilerInput getCompilerInput(CompilerInput aInput);
}
