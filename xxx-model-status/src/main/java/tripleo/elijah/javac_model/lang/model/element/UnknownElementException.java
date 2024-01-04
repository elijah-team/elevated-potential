package tripleo.elijah.javac_model.lang.model.element;

import tripleo.elijah.javac_model.lang.model.UnknownEntityException;

public class UnknownElementException extends UnknownEntityException {
    //private static final long serialVersionUID = 269L;

    private final transient Element element;
    private final transient Object  parameter;

    public UnknownElementException(Element e, Object p) {
        super("Unknown element: \"" + e + "\"");
        element = e;
        this.parameter = p;
    }

    public Element getUnknownElement() {
        return element;
    }

    public Object getArgument() {
        return parameter;
    }
}