package tripleo.elijah.javac_model.lang.model.element;

import tripleo.elijah.javac_model.lang.model.type.TypeMirror;

import java.util.List;

public interface TypeParameterElement extends Element {
    @Override
	TypeMirror asType();

    Element getGenericElement();

    List<? extends TypeMirror> getBounds();

    @Override
    Element getEnclosingElement();
}
