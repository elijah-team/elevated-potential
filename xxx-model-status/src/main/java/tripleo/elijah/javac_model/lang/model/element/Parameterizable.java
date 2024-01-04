package tripleo.elijah.javac_model.lang.model.element;

import java.util.List;

public interface Parameterizable extends Element {
    List<? extends TypeParameterElement> getTypeParameters();
}
