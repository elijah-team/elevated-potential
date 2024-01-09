package tripleo.elijah.javac_model.lang.model.element;

public interface RecordComponentElement extends Element {
	@Override
	Name getSimpleName();

	@Override
	Element getEnclosingElement();

	ExecutableElement getAccessor();
}
