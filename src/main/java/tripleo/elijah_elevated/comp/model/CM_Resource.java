package tripleo.elijah_elevated.comp.model;

import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.util.Operation2;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import tripleo.wrap.File;

public interface CM_Resource {
	int EEXIST = 3;
	int OK     = 5;

	default String getFileNameString() {
		return getFile().toString();
	}

	void setParentResource(CM_ResourceDir aInstructionDir);

	boolean exists();


	CM_ResourceCompute addTree(CM_ResourceCompute2 aCompute2);

	interface CM_Resource_Consumer extends BiConsumer<CM_Resource, Consumer<Operation2<OS_Module>>> {
//		void accept(CM_Resource r, CM_Resource_Consumer x);
	}
	tripleo.wrap.File getFile();

	int processFile(CM_Resource_Consumer consumer);
}
