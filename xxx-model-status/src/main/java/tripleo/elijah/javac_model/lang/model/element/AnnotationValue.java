package tripleo.elijah.javac_model.lang.model.element;

public interface AnnotationValue {

    Object getValue();

    @Override
	String toString();

    <R, P> R accept(AnnotationValueVisitor<R, P> v, P p);
}
