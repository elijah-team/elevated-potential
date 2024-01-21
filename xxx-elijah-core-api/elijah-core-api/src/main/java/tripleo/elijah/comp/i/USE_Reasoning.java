package tripleo.elijah.comp.i;

import tripleo.elijah.ci.*;

import tripleo.elijah.compiler_model.CM_Module;
import tripleo.wrap.File;

public interface USE_Reasoning {

	interface CI extends USE_Reasoning {
		boolean parent();

		File instruction_dir();

		CompilerInstructions compilerInstructions();

		Type ty();
	}

	interface SRC extends USE_Reasoning {
		@Deprecated boolean parent();

		File instruction_dir();

		Type ty();

		CM_Module module();
	}

	enum Type {
		USE_Reasoning__parent, USE_Reasoning__child, USE_Reasoning___default, USE_Reasoning__instruction_doer_addon, USE_Reasoning__initial, USE_Reasoning__findStdLib
	}

	@Deprecated boolean parent();

	CM_Module module();

	File instruction_dir();

	CompilerInstructions compilerInstructions();

	USE_Reasoning.Type ty();
}
