package tripleo.elijah.javac_model.lang.model.element;

import tripleo.elijah.javac_model.lang.model.type.TypeMirror;

import java.util.List;

public interface ExecutableElement extends Element, Parameterizable {
    @Override
    TypeMirror asType();

    @Override
	List<? extends TypeParameterElement> getTypeParameters();

    TypeMirror getReturnType();

    List<? extends VariableElement> getParameters();

    TypeMirror getReceiverType();

    boolean isVarArgs();

    boolean isDefault();

    List<? extends TypeMirror> getThrownTypes();

    AnnotationValue getDefaultValue();

    @Override
    Element getEnclosingElement();

    @Override
    Name getSimpleName();
}
