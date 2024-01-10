package tripleo.elijah.javac_model.lang.model.element;

import tripleo.elijah.javac_model.lang.model.type.TypeMirror;

import java.util.List;

public interface TypeElement extends Element, Parameterizable, QualifiedNameable {
	@Override
	TypeMirror asType();

	@Override
	Name getSimpleName();

	@Override
	Element getEnclosingElement();

	@Override
	List<? extends Element> getEnclosedElements();

	NestingKind getNestingKind();

	@Override
	Name getQualifiedName();

	TypeMirror getSuperclass();

	List<? extends TypeMirror> getInterfaces();

	@Override
	List<? extends TypeParameterElement> getTypeParameters();

	default List<? extends RecordComponentElement> getRecordComponents() {
		return List.of();
	}

	default List<? extends TypeMirror> getPermittedSubclasses() {
		return List.of();
	}
}
