package tripleo.elijah_elevated.comp.model;

import tripleo.wrap.File;

import java.util.function.Consumer;

public interface CM_ResourceDir_Filtered extends CM_ResourceDir {
	void process(Consumer<CM_Resource> consumer);

	@Override
	CM_Resource childFor(File aFile) ;
}
