package tripleo.elijah_elevateder.world.i;

import tripleo.elijah.comp.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah_elevateder.comp.notation.GN_PL_Run2;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah_fluffy.util.Eventual;

public interface WorldModule extends GWorldModule {
	EIT_ModuleInput getEITInput();

	Eventual<GN_PL_Run2.GenerateFunctionsRequest> getErq();

	OS_Module module();

	GN_PL_Run2.GenerateFunctionsRequest rq();
}
