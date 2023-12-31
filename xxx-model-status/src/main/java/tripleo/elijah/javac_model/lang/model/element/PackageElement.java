package tripleo.elijah.javac_model.lang.model.element;

import tripleo.elijah.javac_model.lang.model.type.TypeMirror;

import java.util.List;

public interface PackageElement extends Element, QualifiedNameable {
	@Override
	TypeMirror asType();

	@Override
	Name getSimpleName();

	@Override
	Element getEnclosingElement();

	@Override
	List<? extends Element> getEnclosedElements();

	@Override
	Name getQualifiedName();

	boolean isUnnamed();
}
