package tripleo.elijah.comp.graph;

import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.specs.ElijahSpec;
import tripleo.elijah.g.GLivingRepo;
import tripleo.elijah.g.GWorldModule;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.util.Operation2;

public interface CM_Module {
	void advise(ElijahSpec aSpec);

	void advise(Operation2<OS_Module> aModuleOperation);

	GWorldModule adviseCreator(Compilation0 aCon);

	void adviseWorld(GLivingRepo aWorld);
}
