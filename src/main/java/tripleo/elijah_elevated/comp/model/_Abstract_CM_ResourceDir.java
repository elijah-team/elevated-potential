package tripleo.elijah_elevated.comp.model;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

import tripleo.wrap.File;

public abstract class _Abstract_CM_ResourceDir implements CM_ResourceDir {
	private         CM_ResourceDir parentResource;

	protected abstract Elevated_CM_Factory modelFactory();
	protected abstract Compilation compilation();

	@Override
	public CM_ResourceDir getParentFile() {
		return modelFactory().resourceDir(getFile().getParentFile());
	}

	@Override
	public CM_ResourceDir childDir(final String aDirName) {
		final File dirFile = new File(__baseDir(), aDirName);

		Preconditions.checkArgument(dirFile.isDirectory());

		return modelFactory().resourceDir(dirFile);
	}

	protected abstract File __baseDir();

	@Override
	public String fileToString() {
		return getFile().toString();
	}

	@Override
	public CM_Resource childFor(final File aFile) {
		if (aFile.isDirectory()) {
			return modelFactory().resourceDir(/*this, */aFile);
		}
		return modelFactory().resourceFor(this, aFile);
	}

	@Override
	public CM_Resource childFor(final String aFileName) {
		return childFor(new File(aFileName));
	}

	@Override
	public void setParentResource(final CM_ResourceDir aInstructionDir) {
		parentResource = aInstructionDir;
	}

	@Override
	public boolean exists() {
		return getFile().exists();
	}

	@Override
	public int processFile(final CM_Resource_Consumer consumer) {
		return _Abstract_CM_ResourceDir.OK;
	}

	@Override
	public CM_ResourceCompute addTree(final CM_ResourceCompute2 aCompute2) {
		final CM_Resource  resource = this;
		final @NotNull var cit      = compilation().getInputTree();

		aCompute2.compute(this.compilation());

		return new CM_ResourceCompute() {
			@Override
			public Operation<Ok> compute() { // ??
				cit.addResourceNode(resource, aCompute2);
				return Operation.success(Ok.instance());
			}
		};
	}
}
