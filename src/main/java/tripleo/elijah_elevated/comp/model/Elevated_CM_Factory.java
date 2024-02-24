package tripleo.elijah_elevated.comp.model;

import tripleo.elijah.comp.specs.EzSpec;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.util.Operation;
import tripleo.wrap.File;

import java.util.function.Consumer;

public interface Elevated_CM_Factory {
	Elevated_CM_Module singleModule(OS_Module aModule);

	CM_ResourceDir resourceDir(File aInstructionDir);

	CM_Resource resourceFor(CM_ResourceDir aInstructionDir, File aFile);

	CM_Resource resourceFor(String aFilename);

	EzSpec mkEzSpec(String aFileName, File aFile, Consumer<Operation<EzSpec>> cb);

    CM_Lang getLang(String c);
}
