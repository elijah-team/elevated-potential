package tripleo.elijah.compiler_model;

import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.comp.specs.ElijahSpec;
import tripleo.elijah.g.GLivingRepo;
import tripleo.elijah.g.GWorldModule;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.util.Operation2;

import java.io.InputStream;

public interface CM_Module {
	void advise(ElijahSpec aSpec);

	void advise(Operation2<OS_Module> aModuleOperation);

	GWorldModule adviseCreator(Compilation0 aCon);

	void adviseWorld(GLivingRepo aWorld);

	void advise(LibraryStatementPart aLsp);

	void advise(PreludeProvider preludeProvider);

	InputStream s();

	interface PreludeProvider {
		Operation2<OS_Module> getOperation();
	}

	EIT_ModuleInput getEITInput();
}
