package tripleo.elijah.nextgen.output;

import tripleo.elijah.comp.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.stages.gen_generic.GenerateResult.*;

public interface NG_OutputStatement extends EG_Statement {

	EIT_ModuleInput getModuleInput();

	TY getTy();

	// promise filename
	// promise EOT_OutputFile
}
