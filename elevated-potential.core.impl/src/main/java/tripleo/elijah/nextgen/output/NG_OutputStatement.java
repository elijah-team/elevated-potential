package tripleo.elijah.nextgen.output;

import tripleo.elijah.comp.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.stages.gen_generic.GenerateResult.TY;

public interface NG_OutputStatement extends EG_Statement {

	EIT_ModuleInput getModuleInput();

	TY getTy();

	// promise filename
	// promise EOT_OutputFile
}
