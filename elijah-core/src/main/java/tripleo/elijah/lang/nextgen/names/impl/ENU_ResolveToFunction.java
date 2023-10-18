package tripleo.elijah.lang.nextgen.names.impl;

import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.lang.nextgen.names.i.EN_Understanding;

public record ENU_ResolveToFunction(FunctionDef fd) implements EN_Understanding {
}

public final class ENU_ResolveToFunction implements EN_Understanding {
	private final FunctionDef fd;

	public ENU_ResolveToFunction(FunctionDef fd) {
		this.fd = fd;
	}

	public FunctionDef fd() {
		return fd;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (ENU_ResolveToFunction) obj;
		return Objects.equals(this.fd, that.fd);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fd);
	}

	@Override
	public String toString() {
		return "ENU_ResolveToFunction[" +
				"fd=" + fd + ']';
	}

}
