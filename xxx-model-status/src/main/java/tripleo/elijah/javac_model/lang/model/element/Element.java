package tripleo.elijah.javac_model.lang.model.element;


import tripleo.elijah.javac_model.lang.model.AnnotatedConstruct;
import tripleo.elijah.javac_model.lang.model.type.TypeMirror;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

public interface Element extends AnnotatedConstruct {
    TypeMirror asType();

    ElementKind getKind();

    Set<Modifier> getModifiers();

    Name getSimpleName();

    Element getEnclosingElement();

    List<? extends Element> getEnclosedElements();

    @Override
    boolean equals(Object obj);

    @Override
    int hashCode();

    @Override
    List<? extends AnnotationMirror> getAnnotationMirrors();

    @Override
    <A extends Annotation> A getAnnotation(Class<A> annotationType);

    @Override
    <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType);

    <R, P> R accept(ElementVisitor<R, P> v, P p);
}
