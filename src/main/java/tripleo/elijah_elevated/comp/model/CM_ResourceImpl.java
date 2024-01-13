package tripleo.elijah_elevated.comp.model;

import com.google.common.base.MoreObjects;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.util.Mode;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;
import tripleo.wrap.File;

public class CM_ResourceImpl implements CM_Resource {
	private final File           f;
	private final Compilation    compilation;
	private       CM_ResourceDir parentResource;

	public CM_ResourceImpl(final File aF, final Compilation aCompilation) {
		f           = aF;
		compilation = aCompilation;
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
	public CM_ResourceCompute addTree(final CM_ResourceCompute2 aCompute2) {
		final CM_ResourceImpl resource = this;
		final @NotNull var    cit      = compilation.getInputTree();

		aCompute2.compute(compilation);

		return new CM_ResourceCompute() {
			@Override
			public Operation<Ok> compute() { // !!
				cit.addResourceNode(resource, aCompute2);
				return Operation.success(Ok.instance());
			}
		};
	}

	@Override
	public tripleo.wrap.File getFile() {
		return this.f;
	}

	@Override
	public int processFile(final CM_Resource_Consumer consumer) {
		final int[] ret = {-1};
		consumer.accept(this, u -> {
			if (u.mode() == Mode.SUCCESS) ret[0] = CM_Resource.OK;
		});
		return ret[0];
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
		                  .add("filename", f.getAbsolutePath())
//						  .add("parentResource", parentResource)
                          .toString();
	}
}
