package tripleo.elijah.javac_model.lang.model.type;

import tripleo.elijah.javac_model.lang.model.UnknownEntityException;

public class UnknownTypeException extends UnknownEntityException {

    //private static final long serialVersionUID = 269L;

    private final transient TypeMirror type;
    private final transient Object     parameter;

    public UnknownTypeException(TypeMirror t, Object p) {
        super("Unknown type: \"" + t + "\"");
        type = t;
        this.parameter = p;
    }

    public TypeMirror getUnknownType() {
        return type;
    }

    public Object getArgument() {
        return parameter;
    }
}
