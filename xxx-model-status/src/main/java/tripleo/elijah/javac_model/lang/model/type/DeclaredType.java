package tripleo.elijah.javac_model.lang.model.type;


import tripleo.elijah.javac_model.lang.model.element.Element;

import java.util.List;


public interface DeclaredType extends ReferenceType {

    Element asElement();

    TypeMirror getEnclosingType();

    List<? extends TypeMirror> getTypeArguments();
}
