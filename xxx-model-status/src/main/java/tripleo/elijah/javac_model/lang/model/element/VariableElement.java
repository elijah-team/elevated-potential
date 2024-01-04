package tripleo.elijah.javac_model.lang.model.element;

import tripleo.elijah.javac_model.lang.model.type.TypeMirror;

public interface VariableElement extends Element {
    @Override
    TypeMirror asType();

    Object getConstantValue();

    @Override
    Name getSimpleName();

    @Override
    Element getEnclosingElement();
}
