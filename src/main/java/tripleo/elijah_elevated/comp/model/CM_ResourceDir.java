package tripleo.elijah_elevated.comp.model;

import java.io.FilenameFilter;
import tripleo.wrap.File;

public interface CM_ResourceDir extends CM_Resource {
	CM_ResourceDir_Filtered filter(FilenameFilter aAcceptSourceFiles);

	CM_ResourceDir getParentFile();

	CM_ResourceDir childDir(String aDirName);

	String fileToString();

	CM_Resource childFor(String aFileName);

	@Override
	void setParentResource(CM_ResourceDir aInstructionDir);

	@Override
	tripleo.wrap.File getFile();

	@Override
	int processFile(CM_Resource_Consumer consumer);

	CM_Resource childFor(tripleo.wrap.File aFile);
}
