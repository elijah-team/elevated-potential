package tripleo.elijah.javac_model.lang.model.element;

public interface RecordComponentElement extends Element {
    @Override
    Element getEnclosingElement();

    @Override
    Name getSimpleName();

    ExecutableElement getAccessor();
}
