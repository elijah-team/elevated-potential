package tripleo.elijah_durable_elevated.elijah.world.i;

import tripleo.elijah.*;
import tripleo.elijah.comp.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah_durable_elevated.elijah.comp.notation.GN_PL_Run2;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.OS_Module;

public interface WorldModule extends GWorldModule {
	EIT_ModuleInput getEITInput();

	Eventual<GN_PL_Run2.GenerateFunctionsRequest> getErq();

	OS_Module module();

	GN_PL_Run2.GenerateFunctionsRequest rq();
}
