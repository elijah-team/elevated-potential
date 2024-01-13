package tripleo.elijah_elevated.comp.model;

import tripleo.elijah.comp.Compilation;
import tripleo.elijah.util2.UnintendedUseException;

import java.io.File;
import java.io.FilenameFilter;
import java.util.function.Consumer;

class CM_ResourceDir_FilteredImpl extends _Abstract_CM_ResourceDir implements CM_ResourceDir_Filtered {
	private final CM_ResourceDir CMResourceDir;
	private final tripleo.wrap.File   instructionDir;
	private final FilenameFilter accept_source_files;
	private       CM_ResourceDir parentResource;

	public CM_ResourceDir_FilteredImpl(final CM_ResourceDir aCMResourceDir, final tripleo.wrap.File aInstructionDir, final FilenameFilter aAcceptSourceFiles) {
		CMResourceDir = aCMResourceDir;
		instructionDir = aInstructionDir;
		accept_source_files = aAcceptSourceFiles;
	}

	@Override
	public void process(final Consumer<CM_Resource> consumer) {
		final File           dir   = this.CMResourceDir.getFile().wrapped();
		final java.io.File[] files = dir.listFiles(this.accept_source_files);
		if (files != null) {
			for (final java.io.File file : files) {
				final var resource = CMResourceDir.childFor(tripleo.wrap.File.wrap(file));
				consumer.accept(resource);
			}
		}
	}

	@Override
	public CM_ResourceDir_Filtered filter(final FilenameFilter aAcceptSourceFiles) {
		assert false;
		return null;
	}

	@Override
	protected Elevated_CM_Factory modelFactory() {
		return null;
	}

	@Override
	protected Compilation compilation() {
		throw new UnintendedUseException("FIXME");
	}

	@Override
	public CM_ResourceDir getParentFile() {
		assert false;
		return null;
	}

	@Override
	public CM_ResourceDir childDir(final String aDirName) {
		assert false;
		return null;
	}

	@Override
	protected tripleo.wrap.File __baseDir() {
		return null;
	}

	@Override
	public String fileToString() {
		return CMResourceDir.getFile().toString();
	}

//	@Override
//	public CM_Resource childFor(final java.io.File aFile) {
//		assert false;
//		return null;
//	}

	@Override
	public void setParentResource(final CM_ResourceDir aInstructionDir) {
		parentResource = aInstructionDir;
	}

	@Override
	public CM_ResourceCompute addTree(final CM_ResourceCompute2 aCompute2) {
		return null;
	}

	@Override
	public tripleo.wrap.File getFile() {
		return this.instructionDir;
	}

	@Override
	public int processFile(final CM_Resource_Consumer consumer) {
		consumer.accept(this, x->{
			assert false;
		});
		return OK;
	}
}
