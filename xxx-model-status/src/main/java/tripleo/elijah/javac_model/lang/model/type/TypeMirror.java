package tripleo.elijah.javac_model.lang.model.type;

import tripleo.elijah.javac_model.lang.model.element.AnnotationMirror;
import tripleo.elijah.javac_model.lang.model.AnnotatedConstruct;

import java.lang.annotation.Annotation;
import java.util.List;

public interface TypeMirror extends AnnotatedConstruct {

    TypeKind getKind();

    @Override
	boolean equals(Object obj);

    @Override
	int hashCode();

    @Override
	String toString();

    @Override
    List<? extends AnnotationMirror> getAnnotationMirrors();

    @Override
    <A extends Annotation> A getAnnotation(Class<A> annotationType);

    @Override
    <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType);

    <R, P> R accept(TypeVisitor<R, P> v, P p);
}
