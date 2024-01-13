package tripleo.elijah.comp.graph.i;

import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.comp.specs.ElijahSpec;
import tripleo.elijah.g.GLivingRepo;
import tripleo.elijah.g.GWorldModule;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.util.Operation2;
import tripleo.elijah_elevated.comp.model.Elevated_CM_Module;

import java.io.InputStream;

public interface CM_Module {
	void advise(ElijahSpec aSpec);

	void advise(Operation2<OS_Module> aModuleOperation);

	GWorldModule adviseCreator(Compilation0 aCon);

	void adviseWorld(GLivingRepo aWorld);

	void advise(LibraryStatementPart aLsp);

	void advise(PreludeProvider preludeProvider);

	InputStream s();

	OS_Module _getModule();

	interface PreludeProvider {
		Operation2<OS_Module> getOperation();
	}

	EIT_ModuleInput getEITInput();
}
