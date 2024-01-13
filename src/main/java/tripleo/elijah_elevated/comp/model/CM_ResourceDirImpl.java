package tripleo.elijah_elevated.comp.model;

import tripleo.elijah.comp.Compilation;

import java.io.FilenameFilter;
import tripleo.wrap.File;

public class CM_ResourceDirImpl extends _Abstract_CM_ResourceDir {
	protected final tripleo.wrap.File instructionDir;
	protected final Compilation  compilation;

	public CM_ResourceDirImpl(final tripleo.wrap.File aInstructionDir, final Compilation aCompilation) {
		instructionDir = aInstructionDir;
		compilation    = aCompilation;
	}

	@Override
	public CM_ResourceDir_Filtered filter(final FilenameFilter aFilenameFilter) {
		return new CM_ResourceDir_FilteredImpl(this, instructionDir, aFilenameFilter);
	}

	@Override
	protected Elevated_CM_Factory modelFactory() {
		return compilation.modelFactory();
	}

	@Override
	protected Compilation compilation() {
		return compilation;
	}

	@Override
	protected File __baseDir() {
		return this.instructionDir;
	}

	public tripleo.wrap.File getFile() {
		return instructionDir; // !!
	}
}
