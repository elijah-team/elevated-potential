package tripleo.elijah_durable_elevated.elijah.stages.deduce;

import tripleo.elijah.lang.i.NamespaceStatement;
import tripleo.elijah.lang.i.OS_Element;

import java.util.Objects;

public final class DeduceElementWrapper {
	private final OS_Element element;

	public DeduceElementWrapper(OS_Element element) {
		this.element = element;
	}

	public boolean isNamespaceStatement() {
		return element instanceof NamespaceStatement;
	}

	public OS_Element element() {
		return element;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (DeduceElementWrapper) obj;
		return Objects.equals(this.element, that.element);
	}

	@Override
	public int hashCode() {
		return Objects.hash(element);
	}

	@Override
	public String toString() {
		return "DeduceElementWrapper[" +
				"element=" + element + ']';
	}

}
