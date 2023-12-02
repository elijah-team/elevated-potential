package tripleo.elijah.world.i;

import tripleo.elijah.*;
import tripleo.elijah.comp.notation.GN_PL_Run2;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleInput;

public interface WorldModule extends GWorldModule {
	EIT_ModuleInput getEITInput();

	Eventual<GN_PL_Run2.GenerateFunctionsRequest> getErq();

	EIT_ModuleInput input();

	OS_Module module();

	GN_PL_Run2.GenerateFunctionsRequest rq();
}
