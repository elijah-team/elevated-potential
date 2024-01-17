package tripleo.elijah.javac_model.lang.model.element;

import tripleo.elijah.javac_model.lang.model.type.DeclaredType;

import java.util.Map;

public interface AnnotationMirror {

	DeclaredType getAnnotationType();

	Map<? extends ExecutableElement, ? extends AnnotationValue> getElementValues();
}
